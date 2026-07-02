import java.util.Scanner;

public class Main {
    public static String[] gr = new String[]{" ", ".", ":", "o", "@"};
    public static int weidhWorld = 150;
    public static int heidhWorld = 75;

    public static double fov = 45;

    public static double camX = 75;
    public static double camY = 50;
    public static double camZ = -50;

    public static int Zworld = 200;

    public static int criclex = 75;
    public static int cricley = 50;
    public static int cricleZ = 30;
    public static int cricleShirina = 15;

    public static int groundY = 65;

    public static double lightPosX = 50;
    public static double lightPosY = 25;
    public static double lightPosZ = 10;
    public static double lightRadiys = 28;
    public static double lightImpuls = 8;
    public static double lightImpuls2 = 20;

    public static boolean itsBall = false;

    public static void main(String[] args) {
        String[] red = new String[]{ "\u001B[31m","\u001B[0m"};


        double aspRot = (double) weidhWorld / heidhWorld;
        double fovRad = Math.toRadians(fov);

        Scanner sc = new Scanner(System.in);

        while (true) {
            for (int y = 0; y < heidhWorld; y += 3) {
                for (int x = 0; x < weidhWorld; x++) {
                    int popal = 0;

                    double normX = (2.0 * x / weidhWorld - 1.0) * aspRot;
                    double normY = (2.0 * y / heidhWorld - 1.0);

                    double angX = normX * Math.tan(fovRad / 2);
                    double angY = normY * Math.tan(fovRad / 2);

                    double dirX = angX;
                    double dirY = angY;
                    double dirZ = 1;

                    double n = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
                    dirX /= n;
                    dirY /= n;
                    dirZ /= n;

                    double dis = 0;
                    double hitX = 0, hitY = 0, hitZ = 0;

                    while (dis < Zworld) {
                        double posX = camX + dirX * dis;
                        double posY = camY + dirY * dis;
                        double posZ = camZ + dirZ * dis;


                        double dx = posX - criclex;
                        double dy = posY - cricley;
                        double dz = posZ - cricleZ;

                        if (dx * dx + dy * dy + dz * dz < cricleShirina * cricleShirina || posY >= groundY) {
                            popal = 1;

                            itsBall = false;

                            if (dx * dx + dy * dy + dz * dz < cricleShirina * cricleShirina) {
                                itsBall = true;
                            }

                            double dirLX = lightPosX - posX;
                            double dirLY = lightPosY - posY;
                            double dirLZ = lightPosZ - posZ;

                            double no = Math.sqrt(dirLX * dirLX + dirLY * dirLY + dirLZ * dirLZ);
                            if (no > 0) {
                                dirLX /= no;
                                dirLY /= no;
                                dirLZ /= no;


                                double dist = 0.5;
                                boolean shadow = false;

                                while (dist < no) {
                                    double checkX = posX + dirLX * dist;
                                    double checkY = posY + dirLY * dist;
                                    double checkZ = posZ + dirLZ * dist;

                                    double dLx = checkX - criclex;
                                    double dLy = checkY - cricley;
                                    double dLz = checkZ - cricleZ;


                                    if (dLx * dLx + dLy * dLy + dLz * dLz < cricleShirina * cricleShirina) {
                                        shadow = true;
                                        break;
                                    }

                                    dist += 0.5;
                                }

                                if (!shadow) {

                                    if (dist < lightRadiys) {
                                        popal = 4;
                                    } else if (dist < lightRadiys + lightImpuls) {
                                        popal = 3;
                                    } else if (dist < lightRadiys + lightImpuls + lightImpuls2) {
                                        popal = 2;
                                    } else {
                                        popal = 1;
                                    }
                                }
                            }
                            break;
                        }
                        dis += 0.5;
                    }

                    System.out.print((itsBall ? red[0]: "") + gr[popal] + (itsBall ?  red[1]: ""));
                }
                System.out.print("\n");
            }


            System.out.println("\nwasd - движение, R/F - вверх/вниз");
            System.out.println("Camera: (" + camX + ", " + camY + ", " + camZ + ")");
            String d = sc.nextLine();

            if (d.equals("w")) {
                camZ += 2;
            } else if (d.equals("s")) {
                camZ -= 2;
            } else if (d.equals("d")) {
                camX += 3;
            } else if (d.equals("a")) {
                camX -= 3;
            } else if (d.equals("r")) {
                camY -= 3;
            } else if (d.equals("f")) {
                camY += 3;
            }

        }

    }
}
