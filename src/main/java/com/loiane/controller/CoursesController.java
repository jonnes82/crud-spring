package com.loiane.controller;

import com.loiane.model.Course;
import com.loiane.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CoursesController {
    private final CourseRepository courseRepository;

    public CoursesController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> list(){
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable("id") @NotNull @Positive Long idDoCurso){
        return courseRepository.findById(idDoCurso)
                .map(recordFound -> ResponseEntity.ok().body(recordFound))
                .orElse(ResponseEntity.notFound().build());
    }
//    @PostMapping -- mais atual
//    public ResponseEntity<Course> create(@RequestBody Course course){
//        Course courseCreated = courseRepository.save(course);
//        return ResponseEntity.status(HttpStatus.CREATED).body(courseCreated);
//    }

    /**
     * In this version of method
     * uses annotation @ResponseStatus
     * in return of method we are using the own Entity
     * @param course
     * @return Course
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course createOldVersion(@RequestBody @Valid Course course){
        Course courseCreated = courseRepository.save(course);
        return courseCreated;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable @NotNull Long id, @RequestBody Course course){
        return courseRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(course.getName());
                    recordFound.setCategory(course.getCategory());
                    Course courseUpdated = courseRepository.save(recordFound);
                    return ResponseEntity.ok().body(courseUpdated);
                })
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return courseRepository.findById(id)
                .map(recordFound ->{
                    courseRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
