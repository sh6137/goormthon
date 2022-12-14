package com.good.hareubang.tool.server.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 프로젝트에서 연결된 DB 의 넘버링 전략을 따라간다.
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "user_name")
    private String userName;

    @Column(nullable = false, name = "lati")
    private double lati;

    @Column(nullable = false, name = "longti")
    private double longti;
}
