package ac.kr.tukorea.capstone.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "benchmark")
@Table(name = "benchmark_table")
public class BenchmarkEntity {
    @Id
    @Column(
            name = "ap_name",
            nullable = false
    )

    @Column(
            name = "single_core",
            nullable = false
    )
    private int single_core;

    @Column(
            name = "multi_core",
            nullable = false
    )
    private int multi_core;

    @Column(
            name = "threed_mark",
            nullable = false
    )
    private int threed_mark;

    @OneToMany(mappedBy = "ap_name")
    private Set<PhoneEntity> ap_name;
}
