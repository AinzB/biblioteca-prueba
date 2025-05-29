package com.rockettest.biblioetca.controller;

import com.rockettest.biblioetca.dto.StudentRequest;
import com.rockettest.biblioetca.model.Student;
import com.rockettest.biblioetca.service.StudentService;
import com.rockettest.biblioetca.util.HtmlSanitizer;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    /** Listar todos los estudiantes */
    @GetMapping
    public List<Student> getAll(@RequestParam(value = "studentCode", required = false) String studentCode) {
        if (studentCode != null) {
            return service.searchByStudentCode(studentCode);
        }
        return service.findAll(); 
    }

    /** Obtener un estudiante por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Short id) {
        Student student = service.findById(id);
        return student != null
            ? ResponseEntity.ok(student)
            : ResponseEntity.notFound().build();
    }

    /** Crear un nuevo estudiante con validación */
    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody StudentRequest request) {
        Student std = new Student();
        std.setStudentCode( HtmlSanitizer.stripHtml(request.getStudentCode()) );
        std.setFirstName( HtmlSanitizer.stripHtml(request.getFirstName()) );
        std.setLastName( HtmlSanitizer.stripHtml(request.getLastName()) );
        std.setEmail( HtmlSanitizer.stripHtml(request.getEmail()) );
        std.setPhone( HtmlSanitizer.stripHtml(request.getPhone()) );
        std.setStatus( HtmlSanitizer.stripHtml(request.getStatus()) );
        std.setCreatedAt( new java.sql.Timestamp(System.currentTimeMillis()) );

        Student created = service.create(std);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** Actualizar un estudiante existente */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Short id, @Valid @RequestBody StudentRequest request) {
        Student std = new Student();
        std.setId(id);
        std.setStudentCode( HtmlSanitizer.stripHtml(request.getStudentCode()) );
        std.setFirstName( HtmlSanitizer.stripHtml(request.getFirstName()) );
        std.setLastName( HtmlSanitizer.stripHtml(request.getLastName()) );
        std.setEmail( HtmlSanitizer.stripHtml(request.getEmail()) );
        std.setPhone( HtmlSanitizer.stripHtml(request.getPhone()) );
        std.setStatus( HtmlSanitizer.stripHtml(request.getStatus()) );

        service.update(std);
        return ResponseEntity.noContent().build();
    }

    /** Eliminar un estudiante */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Short id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
