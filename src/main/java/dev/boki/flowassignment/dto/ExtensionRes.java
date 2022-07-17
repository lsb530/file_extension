package dev.boki.flowassignment.dto;

import dev.boki.flowassignment.entity.Basic;
import dev.boki.flowassignment.entity.Custom;
import dev.boki.flowassignment.entity.ExtensionEntity;
import dev.boki.flowassignment.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExtensionRes {

    private Long id;

    private String extension;

    public static ExtensionRes from(ExtensionEntity extensionEntity) {
        ExtensionRes res = null;
        Basic b = null;
        Custom c = null;
        if(extensionEntity.getClass() == Basic.class) {
            b = (Basic) extensionEntity;
            res = new ExtensionRes(b.getId(), b.getExtension());
        } else if(extensionEntity.getClass() == Custom.class) {
            c = (Custom) extensionEntity;
            res = new ExtensionRes(c.getId(), c.getExtension());
        }
        else {
            throw new BadRequestException();
        }
        return res;
    }
}