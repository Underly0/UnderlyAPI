package me.underly0.underlyapi.common.filling;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import me.underly0.underlyapi.common.object.Points;
import me.underly0.underlyapi.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Filling {
    Points points;
    Function<Location, MaterialData> function;
    long delay;

    private final ExecutorService service;

    public Filling(Points points, Function<Location, MaterialData> function, long delay) {
        this.points = points;
        this.function = function;
        this.delay = delay;

        this.service = Executors.newSingleThreadExecutor();
    }

    @SneakyThrows
    public void start(Class<? extends AbstractFilling> clazz) {
        AbstractFilling generator = clazz.getConstructor(Points.class).newInstance(points);

        service.execute(() -> fill(generator));
    }

    @SneakyThrows
    private void fill(AbstractFilling generator) {
        Consumer<Location> consumer = location -> {

            MaterialData material = function.apply(location);
            LocationUtil.setBlock(location, material);

            sleep();
        };

        generator.start(consumer);
    }

    @SneakyThrows
    private void sleep() {
        Thread.sleep(delay);
    }


}
