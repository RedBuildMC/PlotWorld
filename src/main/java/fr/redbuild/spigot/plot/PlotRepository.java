package fr.redbuild.spigot.plot;

import java.util.UUID;

import fr.redbuild.models.spigot.mongo.repository.MongoDBRepository;

public class PlotRepository extends MongoDBRepository<Plot,UUID>{

    public PlotRepository() {
        super(Plot.class, "plot", "redbuild");
    }
    
}
