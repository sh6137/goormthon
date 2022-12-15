package com.good.hareubang.tool.server.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Getter
@Setter
@Table(name = "food_detail")
public class FoodDetail {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 프로젝트에서 연결된 DB 의 넘버링 전략을 따라간다.
    @Column(nullable = false, name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)//연관관계맺음 Many = Many, User =One
    @JoinColumn(name = "user")
    private User user;

    @Column(nullable = false, name = "item")
    private String item;

    @Column(nullable = false, name = "done_ck")
    private boolean doneCk;

    @Column(nullable = false, name = "talk_url")
    private String talkUrl;

    @Column(nullable = false, name = "create_time")
    private LocalDateTime createTime;

    @Column(nullable = false, name = "expiration_time")
    private LocalDateTime expirationTime;

    @Column(nullable = true, name = "buy_time")
    private LocalDateTime buyTime;

    @Column(nullable = false, name = "lati")
    private double lati;

    @Column(nullable = false, name = "longti")
    private double longti;

    @Column(nullable = false, name = "savedPath")
    private String savedPath;

    @Column(nullable = false, name = "food_detail_idx")
    private long foodDetailIdx;

}
