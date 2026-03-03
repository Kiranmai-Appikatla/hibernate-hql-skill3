package com.inventory.util;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.inventory.entity.Product;

public class ProductQueries {

    // Task 2: Insert Sample Products
    public static void loadSampleProducts(Session session) {
        Transaction tx = session.beginTransaction();

        session.persist(new Product("Laptop", 899.99, 15, "Electronics"));
        session.persist(new Product("Mouse", 25.50, 50, "Electronics"));
        session.persist(new Product("Keyboard", 45.00, 30, "Electronics"));
        session.persist(new Product("Monitor", 299.99, 20, "Electronics"));
        session.persist(new Product("Desk Chair", 150.00, 0, "Furniture"));
        session.persist(new Product("Desk Lamp", 35.75, 25, "Furniture"));
        session.persist(new Product("Notebook", 5.99, 100, "Stationery"));
        session.persist(new Product("Pen Set", 12.50, 75, "Stationery"));

        tx.commit();
        System.out.println("Sample products added successfully!");
    }

    // Task 3a: Sort by Price Asc
    public static void sortProductsByPriceAscending(Session session) {
        Query<Product> query = session.createQuery(
                "FROM Product p ORDER BY p.price ASC", Product.class);

        List<Product> products = query.list();
        System.out.println("\n=== Price Ascending ===");
        products.forEach(System.out::println);
    }

    // Task 3b: Sort by Price Desc
    public static void sortProductsByPriceDescending(Session session) {
        Query<Product> query = session.createQuery(
                "FROM Product p ORDER BY p.price DESC", Product.class);

        List<Product> products = query.list();
        System.out.println("\n=== Price Descending ===");
        products.forEach(System.out::println);
    }

    // Task 4: Sort by Quantity Desc
    public static void sortProductsByQuantityDescending(Session session) {
        Query<Product> query = session.createQuery(
                "FROM Product p ORDER BY p.quantity DESC", Product.class);

        List<Product> products = query.list();
        System.out.println("\n=== Quantity Descending ===");
        for (Product p : products) {
            System.out.println(p.getName() + " - Qty: " + p.getQuantity());
        }
    }

    // Task 5: Pagination
    public static void getPaginatedProducts(Session session, int page, int size) {
        Query<Product> query = session.createQuery(
                "FROM Product p", Product.class);

        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        List<Product> products = query.list();

        System.out.println("\n=== Page " + page + " ===");
        products.forEach(System.out::println);
    }

    // Task 6a: Count total
    public static void countTotalProducts(Session session) {
        Long count = session.createQuery(
                "SELECT COUNT(p) FROM Product p", Long.class)
                .uniqueResult();

        System.out.println("\nTotal Products: " + count);
    }

    // Task 6d: Min & Max Price
    public static void findMinMaxPrice(Session session) {
        Object[] result = session.createQuery(
                "SELECT MIN(p.price), MAX(p.price) FROM Product p",
                Object[].class).uniqueResult();

        System.out.println("\nMin Price: $" + result[0]);
        System.out.println("Max Price: $" + result[1]);
    }

    // Task 8: Filter by price range
    public static void filterProductsByPriceRange(Session session,
                                                  double min, double max) {

        Query<Product> query = session.createQuery(
                "FROM Product p WHERE p.price BETWEEN :min AND :max",
                Product.class);

        query.setParameter("min", min);
        query.setParameter("max", max);

        System.out.println("\nProducts Between $" + min + " and $" + max);
        query.list().forEach(p ->
                System.out.println(p.getName() + " - $" + p.getPrice()));
    }

    // Task 9a: LIKE (Starts With)
    public static void findProductsStartingWith(Session session, String prefix) {
        Query<Product> query = session.createQuery(
                "FROM Product p WHERE p.name LIKE :pattern",
                Product.class);

        query.setParameter("pattern", prefix + "%");

        System.out.println("\nStarting with '" + prefix + "'");
        query.list().forEach(p -> System.out.println(p.getName()));
    }
}