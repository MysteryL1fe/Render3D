package com.edu.vsu.khanin.dmitrii.triangulation;

import com.edu.vsu.khanin.dmitrii.exceptions.TooLowVerticesException;
import com.edu.vsu.prilepin.maxim.model.Polygon;

import java.util.ArrayList;

public interface TriangulationAlgorithm {
    ArrayList<Polygon> toTriangles(ArrayList<Polygon> polygons) throws TooLowVerticesException;
}
