package dev.boki.flowassignment.controller;

import dev.boki.flowassignment.data.Basics;
import dev.boki.flowassignment.dto.ExtensionReq.MultipleBasicReq;
import dev.boki.flowassignment.dto.ExtensionReq.MultipleCustomReq;
import dev.boki.flowassignment.dto.ExtensionReq.SingleBasicReq;
import dev.boki.flowassignment.dto.ExtensionReq.SingleCustomReq;
import dev.boki.flowassignment.dto.ExtensionRes;
import dev.boki.flowassignment.exception.BadRequestException;
import dev.boki.flowassignment.repository.BasicRepository;
import dev.boki.flowassignment.repository.CustomRepository;
import dev.boki.flowassignment.service.ExtensionService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class ExtensionController {

    private final ExtensionService extensionService;

    private final BasicRepository basicRepository;

    private final CustomRepository customRepository;

    /**
     * <h1>공통 부분 추출</h1>
     * <pre>조회 부분만 추출</pre>
     */

    @GetMapping("/basics")
    @ResponseStatus(HttpStatus.OK)
    public List<ExtensionRes> findAllBasic() {
        return extensionService.findAllExtension(basicRepository);
    }

    @GetMapping("/basic")
    @ResponseStatus(HttpStatus.OK)
    public ExtensionRes findBasic(@RequestParam(value = "extension") Basics extension) {
        return extensionService.findSingleExtension(basicRepository,
            extension.name().toUpperCase());
    }

    @GetMapping("/customs")
    @ResponseStatus(HttpStatus.OK)
    public List<ExtensionRes> findAllCustom() {
        return extensionService.findAllExtension(customRepository);
    }

    @GetMapping("/custom")
    @ResponseStatus(HttpStatus.OK)
    public ExtensionRes findCustom(@RequestParam(value = "extension") String extension) {
        return extensionService.findSingleExtension(customRepository, extension.toUpperCase());
    }

    /*
        Create && Delete
    */

    // basic
    @PostMapping("/basic")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBasic(@RequestBody SingleBasicReq request) {
        extensionService.addSingleBasicExtension(request.getExtension());
    }

    @PostMapping("/basics")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAllBasics(@RequestBody MultipleBasicReq request) {
        extensionService.addMultipleBasicExtension(request.getExtensions());
    }

    @DeleteMapping("/basic/{extension}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBasic(@PathVariable(value = "extension") Basics extension) {
        extensionService.deleteBasicExtension(extension);
    }

    // custom
    @PostMapping("/custom")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustoms(@RequestBody SingleCustomReq request) {
        Set<String> enums = Basics.getEnums();
        if (enums.contains(request.getExtension().toUpperCase())) {
            throw new BadRequestException("고정 확장자는 커스텀 확장자에 추가할 수 없습니다");
        }
        extensionService.addSingleCustomExtension(request.getExtension());
    }

    @PostMapping("/customs")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAllCustoms(@RequestBody MultipleCustomReq request) {
        Set<String> enums = Basics.getEnums();
        request.getExtensions().removeIf(s -> enums.contains(s.toUpperCase()));
        extensionService.addMultipleCustomExtension(request.getExtensions());
    }

    @DeleteMapping("/custom/{extension}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustom(@PathVariable(value = "extension") String extension) {
        extensionService.deleteCustomExtension(extension);
    }

}