# 3D-java
3Д в консоли на Java

Это одина из самых понятных версий 3Д в консоли которую я смог сделать.

Я посторяюсь обьяснить как это работает:
Сначала мы устанавливаем различные переменые одна из которых fov она в градусах, поэтому ее мы сразу
переводим в радианы потому, что джава считает в них.
Потом уже в цисле для отрисовки пиксилей:

  for (int y = 0; y < heidhWorld; y += 3) {
      for (int x = 0; x < weidhWorld; x++) {
      
мы нормализируем координаты экрана так чтобы серидина была 0 а края 1 и - 1:

double normX = (2.0 * x / weidhWorld - 1.0) * aspRot;
double normY = (2.0 * y / heidhWorld - 1.0);

Потом уже с помощью fov в радианах (fovRad) и тангенсана мы вычисляем угол лучей в зависимости от
растояния от ценра экрана:

double angX = normX * Math.tan(fovRad / 2);
double angY = normY * Math.tan(fovRad / 2);

это нужно для перспективы и для этого мы еще нормализировали координаты экрана
Потом мы создаем три переменные "направления" луча и нормализируем их так чтобы не искожать скорость
лучей:

double dirX = angX;
double dirY = angY;
double dirZ = 1;

double n = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
dirX /= n;
dirY /= n;
dirZ /= n;

Дальше мы просто послойно двигаем лучи и проверяем не врезался ли он во что-то:
double dis = 0;
double hitX = 0, hitY = 0, hitZ = 0;

while (dis < Zworld) {
    double posX = camX + dirX * dis;
    double posY = camY + dirY * dis;
    double posZ = camZ + dirZ * dis;


    double dx = posX - criclex;
    double dy = posY - cricley;
    double dz = posZ - cricleZ;

    if (dx * dx + dy * dy + dz * dz < cricleShirina * cricleShirina || posY >= groundY)
Если он во чтото врезался(по формуле в шар или пол) то он из точки столкновеня пускает луч в сторону источника света (так же нормализируя направления):
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
И так же послойно проверяем есть ли на пути шар, если есть то останавливаем, если нет идем дальше: 
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
Ну и отрисовка в зависимости от растояния от источника освещения:
if (!shadow) {

    if (dist < lightRadiys) {
        popal = 4;
    } else if (dist < lightRadiys + lightImpuls) {
          popal = 3;
    } else if (dist < lightRadiys + lightImpuls + lightImpuls2) {
          popal = 2;
    }else {
         popal = 1;
    }
}

