package dev.boki.flowassignment.service;

import dev.boki.flowassignment.data.Basics;
import dev.boki.flowassignment.dto.ExtensionRes;
import dev.boki.flowassignment.entity.Basic;
import dev.boki.flowassignment.entity.Custom;
import dev.boki.flowassignment.entity.ExtensionEntity;
import dev.boki.flowassignment.exception.DuplicatedExtensionException;
import dev.boki.flowassignment.exception.NotFoundExtensionException;
import dev.boki.flowassignment.repository.BasicRepository;
import dev.boki.flowassignment.repository.CustomRepository;
import dev.boki.flowassignment.repository.ExtensionRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("rawtypes")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ExtensionService {

    private final BasicRepository basicRepository;

    private final CustomRepository customRepository;

    /* 공통 함수로 추출 */
    public List<ExtensionRes> findAllExtension(ExtensionRepository<? extends ExtensionEntity> extensionRepository) {
        return extensionRepository.findAll()
            .stream().map(ExtensionRes::from).collect(Collectors.toList());
    }

    public ExtensionRes findSingleExtension(ExtensionRepository<? extends ExtensionEntity> extensionRepository,
        String condition) {
        return extensionRepository.findEntityByExtension(condition)
            .map(ExtensionRes::from).orElseThrow(NotFoundExtensionException::new);
    }

    /*
        Basic Part
     */
    @Transactional
    public void addSingleBasicExtension(Basics extension) {
        if (basicRepository.findEntityByExtension(extension.name().toUpperCase()).isPresent()) {
            throw new DuplicatedExtensionException();
        }
        Basic basicEntity = Basic.builder().extension(extension.name().toUpperCase()).build();
        basicRepository.save(basicEntity);
    }

    @Transactional
    public void addMultipleBasicExtension(List<Basics> extensions) {
        extensions.forEach(ex -> {
            if (!basicRepository.findEntityByExtension(ex.name().toUpperCase()).isPresent()) {
                Basic basicEntity = Basic.builder().extension(ex.name().toUpperCase()).build();
                basicRepository.save(basicEntity);
            }
        });
    }

    @Transactional
    public void deleteBasicExtension(Basics extension) {
        Basic basicEntity = basicRepository.findEntityByExtension(extension.name().toUpperCase())
            .orElseThrow(NotFoundExtensionException::new);
        basicRepository.delete(basicEntity);
    }


    /*
        Custom Part
     */
    @Transactional
    public void addSingleCustomExtension(String extension) {
        if (customRepository.findEntityByExtension(extension.toUpperCase()).isPresent()) {
            throw new DuplicatedExtensionException();
        }
        Custom customEntity = Custom.builder().extension(extension.toUpperCase()).build();
        customRepository.save(customEntity);
    }

    @Transactional
    public void addMultipleCustomExtension(List<String> extensions) {
        extensions.forEach(ex -> {
            if (!customRepository.findEntityByExtension(ex.toUpperCase()).isPresent()) {
                Custom customEntity = Custom.builder().extension(ex.toUpperCase()).build();
                customRepository.save(customEntity);
            }
        });
    }

    @Transactional
    public void deleteCustomExtension(String extension) {
        Custom customEntity = customRepository.findEntityByExtension(extension.toUpperCase())
            .orElseThrow(NotFoundExtensionException::new);
        customRepository.delete(customEntity);
    }

}