package com.inventory.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.inventory.util.HibernateUtil;
import com.inventory.util.ProductQueries;

public class App {

    public static void main(String[] args) {

        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        // Insert sample data
        ProductQueries.loadSampleProducts(session);

        // Sorting
        ProductQueries.sortProductsByPriceAscending(session);
        ProductQueries.sortProductsByPriceDescending(session);
        ProductQueries.sortProductsByQuantityDescending(session);

        // Pagination
        ProductQueries.getPaginatedProducts(session, 1, 3);

        // Aggregation
        ProductQueries.countTotalProducts(session);
        ProductQueries.findMinMaxPrice(session);

        // Filter
        ProductQueries.filterProductsByPriceRange(session, 20, 100);

        // LIKE
        ProductQueries.findProductsStartingWith(session, "D");

        session.close();
        factory.close();
    }
}