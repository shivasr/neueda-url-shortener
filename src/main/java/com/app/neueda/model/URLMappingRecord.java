package com.app.neueda.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class URLMappingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String originalURL;
    private String shortURL;
}
