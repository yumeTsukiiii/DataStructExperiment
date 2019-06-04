package com.yumetsuki.second.model;

import com.yumetsuki.second.struct.Graph;
import com.yumetsuki.second.struct.Pair;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public final class RailSystem extends Graph<City> {

    private static RailSystem railSystem = new RailSystem();

    ArrayList<Service> services = new ArrayList<>();

    private RailSystem(){}

    public static RailSystem getInstance(){
        return railSystem;
    }

    /**
     * 添加一个城市结点到系统中
     * */
    public boolean addCity(City city){
        return addVertex(city);
    }

    /**
     * 为两个城市添加一条路
     * */
    public boolean addRoute(Service service){
        for (Service service1 : services) {
            if (service1.equals(service)) return false;
        }
        services.add(service);
        Vertex<City> city1 = findVertex(city -> city.getName().equals(service.getStart()));
        Vertex<City> city2 = findVertex(city -> city.getName().equals(service.getEnd()));
        if (city1 == null || city2 == null){
            return false;
        }
        return addEdge(city1, city2, service.getCost());
    }

    /**
     * 找到两个城市的最优路径
     * */
    public Path searchBestPath(String city1, String city2){
        /*判断城市的存在*/
        Vertex<City> v1 = findVertex(city -> city.getName().equals(city1));
        City end = findVertexData(city -> city.getName().equals(city2));
        if (v1 == null|| end == null){
            throw new NoSuchElementException("Cannot find these cities");
        }

        /*进行dijkstra算法，获取结果*/
        ArrayList<DijkstraVertex<City>> results =  dijkstra(v1, end);
        /*从结果中筛选出需要的那一条结果*/
        DijkstraVertex<City> result = results.stream().filter(cityDijkstraVertex -> cityDijkstraVertex.vertex.getData().getName().equals(city2))
                .findFirst().get();

        /*数据映射到最终Path的结果*/
        return mapToPath(result);
    }


    /**
     * 将所有最短路径中抽取出来符合目的地的结果，映射为用于展示的path
     * */
    private Path mapToPath(DijkstraVertex<City> result){

        DijkstraVertex<City> previous = result.previous;

        Path path = new Path();

        while (previous != null){

            path.setCost(previous.cost == Double.MAX_VALUE ? result.cost : result.cost - previous.cost);
            path.setStartCity(previous.vertex.getData());
            path.setEndCity(result.vertex.getData());

            Service service = findService(path.getStartCity().getName(), path.getEndCity().getName());
            if (service == null){
                return null;
            }

            path.setDistance(service.getDistance());

            Path previousPath = new Path();
            path.setPreviousPath(previousPath);
            previousPath.setNextPath(path);
            path = previousPath;

            result = previous;
            previous = previous.previous;

        }

        if (path.getNextPath() == null) return null;

        path.getNextPath().setPreviousPath(null);

        return path.getNextPath();
    }

    /**
     * 方法抽取，用于获取Service（Route）信息
     * */
    private Service findService(String city1, String city2){
        for (Service service : services) {
            if (service.getStart().equals(city1)
                    &&service.getEnd().equals(city2)){
                return service;
            }
        }
        return null;
    }

    /**dijkstra主方法*/
    private ArrayList<DijkstraVertex<City>> dijkstra(Vertex<City> vertex, City city) {
        ArrayList<Vertex<City>> list = new ArrayList<>();
        list.add(vertex);
        ArrayList<DijkstraVertex<City>> dijkstraVertices = new ArrayList<>();
        //初始化这些点
        initVertex(vertex, dijkstraVertices);

        while (true){
            //计算邻接的最短路径的距离和点;
            DijkstraVertex<City> min = null;
            for (Vertex<City> cityVertex : list) {
                min = calculateMin(dijkstraVertices, cityVertex);
                if (min != null) break;
            }

            //更新下一个用于计算最短路径根结点的v的值
            if (min == null) break;
            list.add(min.vertex);
            //优化，找到了该条路径就不再找其它路径+
            if (min.vertex.getData().equals(city)) break;

            //更新最短距离的点
            updateDijkstraVertex(dijkstraVertices, min);
        }
        return dijkstraVertices;
    }

    private void initVertex(Vertex<City> vertex, ArrayList<DijkstraVertex<City>> dijkstraVertices) {
        for (int i = 1; i < vertices.size(); i++) {
            Edge edge = getEdge(vertex, vertices.get(i));
            double cost = (edge != null) ? edge.getWeight() : Double.MAX_VALUE;
            dijkstraVertices.add(new DijkstraVertex<>(
                    edge != null ? new DijkstraVertex<>(null, vertex, Double.MAX_VALUE, true): null,
                    vertices.get(i),
                    cost,
                    false
            ));
        }
    }

    /**
     * dijkstra算法中寻找最短邻接点
     * */
    private DijkstraVertex<City> calculateMin(ArrayList<DijkstraVertex<City>> dijkstraVertices, Vertex<City> v){
        DijkstraVertex<City> minCostVertex = null;
        double minCost = Double.MAX_VALUE;

        for (int i = 0; i < dijkstraVertices.size(); i++) {
            DijkstraVertex<City> dijkstraVertex = dijkstraVertices.get(i);
            if (dijkstraVertex.complete && !dijkstraVertex.vertex.getData().equals(v.getData())) continue;
            Edge edge = getEdge(v, dijkstraVertex.vertex);
            if (edge != null){
                if (edge.getWeight() < minCost){
                    minCost = edge.getWeight();
                    minCostVertex = dijkstraVertex;
                }
            }
        }
        if (minCostVertex != null) minCostVertex.complete = true;

        return minCostVertex;
    }

    /**
     * 更新所有结点到起点的距离
     * */
    private void updateDijkstraVertex(
            ArrayList<DijkstraVertex<City>> dijkstraVertices,
            DijkstraVertex<City> min
    ) {
        for (DijkstraVertex<City> dijkstraVertex : dijkstraVertices) {
            Edge edge = getEdge(min.vertex, dijkstraVertex.vertex);
            if (edge != null){
                if ((edge.getWeight() + min.cost) < dijkstraVertex.cost){
                    dijkstraVertex.cost = edge.getWeight() + min.cost;
                    dijkstraVertex.previous = new DijkstraVertex<>(min.previous, min.vertex, min.cost, false);
                }
            }
        }

    }

    private static class DijkstraVertex<T> {
        DijkstraVertex<T> previous;
        Vertex<T> vertex;
        double cost;
        boolean complete;

        DijkstraVertex(DijkstraVertex<T> previous, Vertex<T> vertex, double cost, boolean complete) {
            this.previous = previous;
            this.vertex = vertex;
            this.cost = cost;
            this.complete = complete;
        }

    }

}
