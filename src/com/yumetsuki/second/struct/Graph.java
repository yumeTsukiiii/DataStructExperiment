package com.yumetsuki.second.struct;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Graph<T> {

    protected ArrayList<Vertex<T>> vertices;

    public Graph() {
        vertices = new ArrayList<>();
    }

    public Graph(ArrayList<Vertex<T>> vertices) {
        this.vertices = vertices;
    }

    /**
     * 判断是否邻接
     * */
    public boolean isAdjacency(Vertex<T> v1, Vertex<T> v2){
        Edge edge = v1.head;
        while (edge != null){
            if (vertices.get(edge.vertexIndex).equals(v2)){
                return true;
            } else {
                edge = edge.next;
            }
        }
        return false;
    }

    /**
     * 得到一条边
     * */
    public Edge getEdge(Vertex<T> v1, Vertex<T> v2){
        Edge edge = v1.head;
        while (edge != null){
            if (!vertices.get(edge.vertexIndex).equals(v2)){
                edge = edge.next;
            } else {
                break;
            }
        }
        return edge;
    }

    /**
     * 深度优先遍历
     * */
    public void forEach(Consumer<T> consumer){
        boolean []lookedIndex = new boolean[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            if (!lookedIndex[i]){
                consumer.accept(vertices.get(i).data);
                lookedIndex[i] = true;
            }
            forEachAdjacency(lookedIndex, vertices.get(i).head, consumer);
        }
    }

    /**
     * 找到一个结点的数据
     * */
    public T findVertexData(Predicate<T> predicate){
        for (Vertex<T> vertex : vertices) {
            if (predicate.test(vertex.data))
                return vertex.data;
        }
        return null;
    }

    /**
     * 找到一个结点
     * */
    public Vertex<T> findVertex(Predicate<T> predicate){
        for (Vertex<T> vertex : vertices) {
            if (predicate.test(vertex.data))
                return vertex;
        }
        return null;
    }

    /**
     * 添加一个结点
     * */
    public boolean addVertex(T data){
        if (vertices.stream()
                .filter((tVertex -> tVertex.data.equals(data)))
                .toArray().length > 0)
        {
            return false;
        }
        vertices.add(new Vertex<>(data, null));
        return true;
    }

    /**
     * 添加一个结点
     * */
    public boolean addVertex(Vertex<T> vertex){
        if (vertices.stream()
                .filter((tVertex -> tVertex.data.equals(vertex.data)))
                .toArray().length > 0)
        {
            return false;
        }
        vertices.add(vertex);
        return true;
    }

    /**
     * 添加一条边
     * */
    public boolean addEdge(Vertex<T> v1, Vertex<T> v2, double weight){
        if (hasEdge(v1, v2)){
            return false;
        }
        if (v1.head != null) {
            Edge edge = v1.head;
            v1.head = new Edge(vertices.indexOf(v2), weight, edge);
        } else {
            v1.head = new Edge(vertices.indexOf(v2), weight, null);
        }
        return true;
    }

    /**
     * 判断是否有边
     * */
    public boolean hasEdge(Vertex<T> v1, Vertex<T> v2){
        Edge edge = v1.head;
        while (edge != null){
            if (vertices.get(edge.vertexIndex) != v2){
                edge = edge.next;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 遍历每个邻接点
     * */
    private void forEachAdjacency(boolean [] lookedIndex, Edge edge, Consumer<T> consumer){
        if (edge == null) return;
        Vertex<T> vertex = vertices.get(edge.vertexIndex);
        if (!lookedIndex[edge.vertexIndex]){
            consumer.accept(vertex.data);
            lookedIndex[edge.vertexIndex] = true;
        }

        forEachAdjacency(lookedIndex, vertices.get(edge.vertexIndex).getHead(), consumer);
        forEachAdjacency(lookedIndex, edge.next, consumer);
    }

    public static class Vertex<T> {
        T data;
        Edge head;
        public Vertex(T data, Edge head) {
            this.data = data;
            this.head = head;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Edge getHead() {
            return head;
        }

        public void setHead(Edge head) {
            this.head = head;
        }
    }

    public static class Edge {
        int vertexIndex;
        double weight;
        Edge next;
        public Edge(int vertexIndex, double weight, Edge next) {
            this.vertexIndex = vertexIndex;
            this.weight = weight;
            this.next = next;
        }

        public int getVertexIndex() {
            return vertexIndex;
        }

        public void setVertexIndex(int vertexIndex) {
            this.vertexIndex = vertexIndex;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public Edge getNext() {
            return next;
        }

        public void setNext(Edge next) {
            this.next = next;
        }
    }

}
