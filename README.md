Team ini repo pakek traning aja, bukan app sebenarnya.

## Persiapan
1. Punya IDE Intelijj dan terpasang extension Quarkus. Extension docker juga.
2. Docker dan Docker Compose perlu diinstall
3. Db Beaver
4. Pertama buat db, buka folder root/doc/docker/db.
5. Buka file docker-compose.yml -> Klik tombol run -> tunggu selesai
6. Buka terminal (alt + f12) -> ketik ./mvnw flyway:migrate -> tunggu selesai
7. Copy .env.example -> paste dengan nama .env -> rubah field disana
8. Jalankan Quarkus