package io.github.joxit.osm.entities;

public class Feature {
    private String type;
    private Geometry geometry;
    private Propertie properties;

    public Feature(String type, Geometry geometry, Propertie properties) {
        this.type = type;
        this.geometry = geometry;
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Propertie getProperties() {
        return properties;
    }

    public void setProperties(Propertie properties) {
        this.properties = properties;
    }
}
