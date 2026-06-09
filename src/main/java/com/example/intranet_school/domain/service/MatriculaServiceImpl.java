package com.example.intranet_school.domain.service;

import com.example.intranet_school.application.dto.AlumnoMatriculaRequest;
import com.example.intranet_school.application.dto.MatriculaCreateRequest;
import com.example.intranet_school.domain.model.*;
import com.example.intranet_school.domain.ports.in.MatriculaUseCase;
import com.example.intranet_school.domain.ports.out.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MatriculaServiceImpl implements MatriculaUseCase {

    private final MatriculaRepositoryPort matriculaRepositoryPort;
    private final EstudianteRepositoryPort estudianteRepositoryPort;
    private final PadreRepositoryPort padreRepositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncryptorPort passwordEncryptorPort;
    private final MensualidadRepositoryPort mensualidadRepositoryPort;

    @Override
    public List<Matricula> getAllMatriculas() {
        return matriculaRepositoryPort.findAll();
    }

    @Override
    public Optional<Matricula> getMatriculaById(Long id) {
        return matriculaRepositoryPort.findById(id);
    }

    @Override
    public List<Matricula> crearMatriculas(MatriculaCreateRequest request) {
        if (request.getAlumnos() == null || request.getAlumnos().isEmpty()) {
            throw new IllegalArgumentException("Debe incluir al menos un alumno");
        }

        for (AlumnoMatriculaRequest alumno : request.getAlumnos()) {
            if (usuarioRepositoryPort.existsByEmail(alumno.getEmail())) {
                throw new IllegalArgumentException("El correo ya está registrado: " + alumno.getEmail());
            }
        }

        Padre padre = resolverPadre(request);

        List<Matricula> resultado = new ArrayList<>();
        for (AlumnoMatriculaRequest alumnoReq : request.getAlumnos()) {
            resultado.add(crearAlumnoYMatricula(alumnoReq, padre, request.getDiaPago()));
        }
        return resultado;
    }

    private Padre resolverPadre(MatriculaCreateRequest request) {
        if (request.getPadreEmail() == null || request.getPadreEmail().isBlank()) return null;

        Optional<Usuario> existente = usuarioRepositoryPort.findByEmail(request.getPadreEmail());
        if (existente.isPresent()) {
            return padreRepositoryPort.findByUsuarioId(existente.get().getId()).orElse(null);
        }

        Usuario padreUsuario = new Usuario();
        padreUsuario.setEmail(request.getPadreEmail());
        padreUsuario.setPassword(passwordEncryptorPort.encode(request.getPadrePassword()));
        padreUsuario.setNombre(request.getPadreNombre());
        padreUsuario.setApellido(request.getPadreApellidoPaterno());
        padreUsuario.setApellidoMaterno(request.getPadreApellidoMaterno());
        padreUsuario.setRol(Usuario.Rol.PADRE);
        padreUsuario.setAvatar("https://ui-avatars.com/api/?name="
                + request.getPadreNombre() + "+" + request.getPadreApellidoPaterno() + "&background=random");
        padreUsuario = usuarioRepositoryPort.save(padreUsuario);

        Padre padre = new Padre();
        padre.setUsuario(padreUsuario);
        padre.setDni(request.getPadreDni());
        padre.setTelefono(request.getPadreTelefono());
        padre.setActivo(true);
        padre.setHijos(new ArrayList<>());
        return padreRepositoryPort.save(padre);
    }

    private Matricula crearAlumnoYMatricula(AlumnoMatriculaRequest req, Padre padre, Integer diaPago) {
        // Crear usuario del alumno
        Usuario alumnoUsuario = new Usuario();
        alumnoUsuario.setEmail(req.getEmail());
        alumnoUsuario.setPassword(passwordEncryptorPort.encode(req.getPassword()));
        alumnoUsuario.setNombre(req.getNombre());
        alumnoUsuario.setApellido(req.getApellidoPaterno());
        alumnoUsuario.setApellidoMaterno(req.getApellidoMaterno());
        alumnoUsuario.setRol(Usuario.Rol.ESTUDIANTE);
        alumnoUsuario.setAvatar("https://ui-avatars.com/api/?name="
                + req.getNombre() + "+" + req.getApellidoPaterno() + "&background=random");
        alumnoUsuario = usuarioRepositoryPort.save(alumnoUsuario);

        // Crear perfil Estudiante
        Estudiante estudiante = new Estudiante();
        estudiante.setUsuario(alumnoUsuario);
        estudiante.setDni(req.getDni());
        estudiante.setFechaNacimiento(req.getFechaNacimiento());
        estudiante.setGrado(req.getGrado());
        estudiante.setSeccion(req.getSeccion());
        if (req.getNivel() != null) {
            estudiante.setNivel(Estudiante.NivelEducativo.valueOf(req.getNivel()));
        }
        estudiante = estudianteRepositoryPort.save(estudiante);

        // Vincular al padre
        if (padre != null) {
            if (padre.getHijos() == null) padre.setHijos(new ArrayList<>());
            padre.getHijos().add(estudiante);
            padreRepositoryPort.save(padre);
        }

        // Crear matrícula (una por alumno)
        Matricula matricula = new Matricula();
        matricula.setEstudiante(estudiante);
        matricula.setAño(req.getAnio() != null ? req.getAnio() : LocalDateTime.now().getYear());
        matricula.setGrado(req.getGrado());
        if (req.getNivel() != null) {
            matricula.setNivel(Matricula.NivelEducativo.valueOf(req.getNivel()));
        }
        matricula.setEstado(Matricula.Estado.ACTIVA);
        matricula.setEstadoPago(Matricula.EstadoPago.PENDIENTE);
        matricula.setMontoMatricula(req.getMontoMatricula());
        matricula.setMontoMensualidad(req.getMontoMensualidad());
        matricula.setDiaPago(diaPago != null ? diaPago : 15);

        return matriculaRepositoryPort.save(matricula);
    }

    @Override
    public Matricula pagarMatricula(Long id) {
        Matricula matricula = matriculaRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula no encontrada: " + id));

        if (matricula.getEstadoPago() == Matricula.EstadoPago.PAGADO) {
            throw new IllegalStateException("La matrícula ya fue pagada");
        }

        matricula.setEstadoPago(Matricula.EstadoPago.PAGADO);
        Matricula saved = matriculaRepositoryPort.save(matricula);

        mensualidadRepositoryPort.saveAll(generarMensualidades(saved));

        return saved;
    }

    private List<Mensualidad> generarMensualidades(Matricula matricula) {
        Mensualidad.Mes[] meses = {
            Mensualidad.Mes.MARZO, Mensualidad.Mes.ABRIL, Mensualidad.Mes.MAYO,
            Mensualidad.Mes.JUNIO, Mensualidad.Mes.JULIO, Mensualidad.Mes.AGOSTO,
            Mensualidad.Mes.SEPTIEMBRE, Mensualidad.Mes.OCTUBRE, Mensualidad.Mes.NOVIEMBRE,
            Mensualidad.Mes.DICIEMBRE
        };

        int diaPago = matricula.getDiaPago() != null ? matricula.getDiaPago() : 15;

        List<Mensualidad> resultado = new ArrayList<>();
        for (Mensualidad.Mes mes : meses) {
            YearMonth ym = YearMonth.of(matricula.getAño(), mes.numero());
            int diaReal = Math.min(diaPago, ym.lengthOfMonth());

            Mensualidad m = new Mensualidad();
            m.setMatricula(matricula);
            m.setMes(mes);
            m.setAño(matricula.getAño());
            m.setMonto(matricula.getMontoMensualidad());
            m.setEstadoPago(Mensualidad.EstadoPago.PENDIENTE);
            m.setFechaVencimiento(LocalDate.of(matricula.getAño(), mes.numero(), diaReal));
            resultado.add(m);
        }
        return resultado;
    }

    @Override
    public Optional<Matricula> getMiMatricula(String email) {
        return matriculaRepositoryPort.findActivaByUsuarioEmail(email);
    }

    @Override
    public void deleteMatricula(Long id) {
        matriculaRepositoryPort.deleteById(id);
    }
}
