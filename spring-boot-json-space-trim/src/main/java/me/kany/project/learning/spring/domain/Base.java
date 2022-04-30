package me.kany.project.learning.spring.domain;

import lombok.Data;
import me.kany.project.learning.spring.annotation.SpacesField;

/**
 * @author Jason.Wang
 * @date 2022/3/31 18:06
 */
@Data
public class Base {
    @SpacesField
    private String address;
}
