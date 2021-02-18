package com.treelevel.app.training.controller;

import com.treelevel.app.training.controller.exception.ProgramNotFoundException;
import com.treelevel.app.training.model.Program;
import com.treelevel.app.training.repository.ProgramRepository;
import com.treelevel.app.training.utils.PdfManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.List;

@CrossOrigin
@RequestMapping("/programs")
@RestController
public class ProgramController {

    private final ProgramRepository repository;

    ProgramController(ProgramRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    List<Program> all() {
        return repository.findAll();
    }

    @PostMapping
    Program newProgram(@RequestBody Program newProgram) {
        return repository.save(newProgram);
    }

    @GetMapping("/{id}")
    Program one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ProgramNotFoundException(id));
    }


    @DeleteMapping("/{id}")
    void deleteProgram(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping(value = "/getpdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public void generatePdf(@PathVariable Long id) throws Exception {

        Program program =  repository.findById(id).orElseThrow( () -> new Exception("Can't find PDF"));

        String outputFolder = "src/main/resources/pdf/programs/" + program.getName().toUpperCase().replace(" ", "_") + "-" +  program.getId() + ".pdf";

        OutputStream outputStream;
        outputStream = new FileOutputStream(new File(outputFolder));

        Context context  = new Context();
        context.setVariable("name", program.getName());
        context.setVariable("ref", program.getRef());
        context.setVariable("content", URLDecoder.decode(program.getContent(), "UTF-8"));
        context.setVariable("days", program.getDays());
        context.setVariable("priceInter", program.getPriceInter());
        context.setVariable("priceIntra", program.getPriceIntra());
        context.setVariable("prerequisites", program.getPrerequisites());
        context.setVariable("objectives", program.getObjectives());

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(PdfManager.parseThymeleafTemplate("training-program", context));
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }

      /* TODO: Example put mapping... don't really need
    @PutMapping("/{id}")
    Program replaceProgram(@RequestBody Program newProgram, @PathVariable Long id) {

        return repository.findById(id)
                .map(program -> {
                    employee.setName(newProgram.getName());
                    employee.setRole(newProgram.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newProgram.setId(id);
                    return repository.save(newProgram);
                });
    }*/
}
