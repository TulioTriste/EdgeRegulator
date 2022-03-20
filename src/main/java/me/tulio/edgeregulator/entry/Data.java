package me.tulio.edgeregulator.entry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.tulio.edgeregulator.cuboid.Cuboid;

@Getter
@RequiredArgsConstructor
public class Data {

    public final String region;
    public final Cuboid cuboid;
}
