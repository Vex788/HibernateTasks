import menu_aurora_class.Menu;
import universal_dao.Cost;
import universal_dao.Discount;
import universal_dao.Weight;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // create connection
            emf = Persistence.createEntityManagerFactory("JPATest");
            em = emf.createEntityManager();
            while (true) {
                System.out.println("1: add client");
                System.out.println("2: add random clients");
                System.out.println("3: delete client");
                System.out.println("4: change client");
                System.out.println("5: view clients");
                System.out.println("6: sort");
                System.out.print("-> ");

                String s = sc.nextLine().trim();
                switch (s) {
                    case "1":
                        addDish(sc);
                        break;
                    case "2":
                        insertRandomDishs(sc);
                        break;
                    case "3":
                        deleteDish(sc);
                        break;
                    case "4":
                        changeDish(sc);
                        break;
                    case "5":
                        viewMenu();
                        break;
                    case "6":
                        sortMenu(sc);
                        break;
                    default:
                        return;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        } finally {
            sc.close();
            em.close();
            emf.close();
        }
    }

    private static void addDish(Scanner sc) {
        System.out.print("Enter dish name: ");
        String name = sc.nextLine();
        System.out.print("Enter dish weight: ");
        String weightS = sc.nextLine();
        System.out.print("Enter dish cost: ");
        String costS = sc.nextLine();
        System.out.print("Enter discount on dish: ");
        String discountS = sc.nextLine();

        int weight = Integer.parseInt(weightS);
        int cost = Integer.parseInt(costS);
        int discount = Integer.parseInt(discountS);

        em.getTransaction().begin();
        try {
            menu_aurora_class.Menu c = new menu_aurora_class.Menu(name, weight, cost, discount);
            em.persist(c);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void deleteDish(Scanner sc) {
        System.out.print("Enter dish id: ");
        String sId = sc.nextLine();
        long id = Long.parseLong(sId);

        menu_aurora_class.Menu c = em.find(menu_aurora_class.Menu.class, id);
        if (c == null) {
            System.out.println("Client not found!");
            return;
        }

        em.getTransaction().begin();
        try {
            em.remove(c);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void changeDish(Scanner sc) {
        System.out.print("Enter dish name: ");
        String name = sc.nextLine();

        System.out.print("Enter weight: ");
        String weightS = sc.nextLine();

        System.out.print("Enter cost: ");
        String costS = sc.nextLine();

        System.out.print("Enter discount: ");
        String discountS = sc.nextLine();

        int weight = Integer.parseInt(weightS);
        int cost = Integer.parseInt(weightS);
        int discount = Integer.parseInt(discountS);

        menu_aurora_class.Menu menu = null;
        try {
            Query query = em.createQuery("SELECT menu FROM menu_aurora_class.Menu menu WHERE menu.name = :name", menu_aurora_class.Menu.class);
            query.setParameter("name", name);
            menu = (menu_aurora_class.Menu) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Dish not found!");
            return;
        } catch (NonUniqueResultException ex) {
            System.out.println("Non unique result!");
            return;
        }

        em.getTransaction().begin();
        try {
            menu.setWeight(weight);
            menu.setCost(cost);
            menu.setDiscount(discount);

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void insertRandomDishs(Scanner sc) {
        System.out.print("Enter dish count: ");
        String sCount = sc.nextLine();
        int count = Integer.parseInt(sCount);

        em.getTransaction().begin();
        try {
            for (int i = 0; i < count; i++) {
                menu_aurora_class.Menu menu = new menu_aurora_class.Menu(
                        "name" + (i + 1),
                        RND.nextInt(1000),
                        RND.nextInt(3000),
                        RND.nextInt(50));
                em.persist(menu);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void viewMenu() {
        Query query = em.createQuery("SELECT c FROM menu_aurora_class.Menu c", menu_aurora_class.Menu.class);
        List<menu_aurora_class.Menu> list = (List<menu_aurora_class.Menu>) query.getResultList();

        for (menu_aurora_class.Menu menu : list)
            System.out.println(menu);
    }

    private static void sortMenu(Scanner sc) throws IllegalAccessException {
        Query query = em.createQuery("SELECT c FROM menu_aurora_class.Menu c", menu_aurora_class.Menu.class);
        List<menu_aurora_class.Menu> list = (List<menu_aurora_class.Menu>) query.getResultList();

        System.out.println("Get weight (1), sort cost range (2), get discount (3): ");
        String sortFlag = sc.nextLine();

        switch (sortFlag) {
            case "1":
                System.out.println("Enter weight (kg): ");
                int weight = sc.nextInt();

                list = GetDataInRange.get(list, Weight.class, weight, 0);
                break;
            case "2":
                System.out.println("Enter cost range\nmin: ");
                int min = sc.nextInt();
                System.out.println("max: ");
                int max = sc.nextInt();

                list = GetDataInRange.get(list, Cost.class, min, max);
                break;
            case "3":
                System.out.println("Enter discount (0-50)%: ");
                int discount = sc.nextInt();

                list = GetDataInRange.get(list, Discount.class, discount, 0);
                break;
            default:
                break;
        }

        for (menu_aurora_class.Menu menu : list) {
            System.out.println(menu.toString());
        }
    }

    static final Random RND = new Random();
}


