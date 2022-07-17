package dev.boki.flowassignment.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class ExtensionEntity {
    @Column(unique = true, nullable = false)
    public String extension;
}