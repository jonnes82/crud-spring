package com.loiane.controller;

import com.loiane.model.Course;
import com.loiane.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Course> findById(@PathVariable("id") Long idDoCurso){
        return courseRepository.findById(idDoCurso)
                .map(record -> ResponseEntity.ok().body(record))
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
    public Course createOldVersion(@RequestBody Course course){
        Course courseCreated = courseRepository.save(course);
        return courseCreated;
    }

}
