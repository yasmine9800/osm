package io.github.joxit.osm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.joxit.osm.entities.EntiteJson;
import io.github.joxit.osm.entities.Feature;
import io.github.joxit.osm.entities.Geometry;
import io.github.joxit.osm.entities.Propertie;
import io.github.joxit.osm.model.Tile;
import io.github.joxit.osm.utils.Svg;
import mil.nga.sf.geojson.FeatureConverter;
import mil.nga.sf.geojson.GeoJsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service pour retourner les tuiles.
 *
 * @author Jones Magloire @Joxit
 * @since 2019-11-03
 */
@Service
public  class TileService {
  @Autowired
  ResourceLoader resourceLoader;


  ObjectMapper mapper = new ObjectMapper();
   private EntiteJson entiteJson = getData();
  /**
   * Ici il faut prendre les coordonnées de la tuile et renvoyer la donnée PNG associée.
   * Vous pouvez y ajouter des fonctionnalités en plus pour améliorer les perfs.
   *
   * @param tile qu'il faut renvoyer
   *
   * @return le byte array au format png
   */
  public byte[] getTile(Tile tile) {
    if (tile.getX()<0 || tile.getY()<0 || tile.getZ()<0) throw new IllegalArgumentException("you must enter positive numbers");
    if (tile.getZ()>24) throw new IllegalArgumentException("z must not exceed 24");
    if( tile.getX()>Math.pow(tile.getZ(),2)) throw new IllegalArgumentException("valeurs de x  trop grand");
    if(tile.getY()>Math.pow(tile.getZ(),2) ) throw new IllegalArgumentException("valeurs de y trop grands");
    return Svg.getTile(tile);
  }

  /**
   * @return le contenu du fichier prefectures.geojson
   */
  public String getPrefectures() throws IOException {
    JsonNode jsonNode = mapper.readValue(new FileInputStream(loadResource().getFile()) , JsonNode.class);
    String content =mapper.writeValueAsString(jsonNode);
    return content;
  }

  /**
   * Il faudra créer votre DAO pour récuperer les données.
   * Utilisez ce que vous voulez pour faire le DAO.
   *
   * @return les éléments contenus dans la base de données
   */
  public GeoJsonObject getPOIs() throws JsonProcessingException {
    String content = mapper.writeValueAsString(entiteJson);
    GeoJsonObject geoJsonObject = FeatureConverter.toGeoJsonObject(content);
    return geoJsonObject;
  }

  private EntiteJson  getData() {
    List<Feature> features = new ArrayList<Feature>();
    Geometry geometry = new Geometry("Point",new Double[] {1D,1D});
    Geometry geometry1 = new Geometry("Point",new Double[] {2D,2D});
    Geometry geometry2 = new Geometry("Point",new Double[] {3D,3D});
    Geometry geometry3 = new Geometry("Point",new Double[] {4D,4D});
    Geometry geometry4 = new Geometry("Point",new Double[] {5D,5D});
    Geometry geometry5 = new Geometry("Point",new Double[] {6D,6D});
    Propertie propertie = new  Propertie(1, "Ones");
    Propertie propertie1 = new  Propertie(6, "Ones");
    Propertie propertie2 = new  Propertie(2, "Ones");
    Propertie propertie3 = new  Propertie(3, "Ones");
    Propertie propertie4 = new  Propertie(4, "Ones");
    Propertie propertie5 = new  Propertie(5, "Ones");

    Feature feature = new Feature (
            "Feature",
            geometry,
            propertie
    );
    Feature feature1 = new Feature (
            "Feature",
            geometry1,
            propertie1
    );
    Feature feature2 = new Feature (
            "Feature",
            geometry2,
            propertie2
    );
    Feature feature3 = new Feature (
            "Feature",
            geometry3,
            propertie3
    );
    Feature feature4 = new Feature (
            "Feature",
            geometry4,
            propertie4
    );
    Feature feature5 = new Feature (
            "Feature",
            geometry5,
            propertie5
    );
    features.add(feature);
    features.add(feature1);
    features.add(feature2);
    features.add(feature3);
    features.add(feature4);
    features.add(feature5);
     EntiteJson entiteJson = new EntiteJson (
            "FeatureCollection",
            features
   );
    return entiteJson;
  }

  public org.springframework.core.io.Resource loadResource() {
    return resourceLoader.getResource("classpath:prefectures.geojson");
  }
}
