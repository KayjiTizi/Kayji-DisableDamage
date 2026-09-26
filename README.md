# Kayji-DisableDamage

Plugin Minecraft (Spigot) vô hiệu hoá sát thương nổ lên người chơi — chặn TNT và Respawn Anchor, bật/tắt trực tiếp trong game bằng lệnh.

> **Tác giả:** Kayji_tizi · **Phiên bản:** 1.0 · **API:** 1.20 · **Java:** 16

## Tính năng

- **Chặn sát thương TNT** (`disable_tnt_damage`): hủy sự kiện nổ khối (`BLOCK_EXPLOSION`) lên người chơi.
- **Chặn sát thương Respawn Anchor** (`disable_respawn_anchor_damage`): hủy sự kiện nổ (`ENTITY_EXPLOSION`) khi người chơi ở vị trí Respawn Anchor.
- **Bật/tắt toàn bộ plugin** không cần restart (`plugin_enabled`).
- Mọi thay đổi bằng lệnh được **lưu ngay vào `config.yml`** và giữ nguyên sau khi restart.
- Kiểm tra quyền `disabledamage.use` — chỉ **người chơi** dùng được lệnh (console sẽ nhận hướng dẫn).

## Bảng lệnh

Gõ trong game với dấu `/`:

| Lệnh | Quyền | Mô tả |
| --- | --- | --- |
| `/disabledamage on` | `disabledamage.use` | Bật toàn bộ plugin (bật chặn sát thương) |
| `/disabledamage off` | `disabledamage.use` | Tắt toàn bộ plugin (sát thương trở lại bình thường) |
| `/disabledamage tnt on` | `disabledamage.use` | Bật chặn sát thương do TNT nổ |
| `/disabledamage tnt off` | `disabledamage.use` | Tắt chặn sát thương do TNT nổ |
| `/disabledamage anchor on` | `disabledamage.use` | Bật chặn sát thương do Respawn Anchor nổ |
| `/disabledamage anchor off` | `disabledamage.use` | Tắt chặn sát thương do Respawn Anchor nổ |

> Quyền `disabledamage.use` — mặc định chỉ `op`.

## Cấu hình

```yaml
plugin_enabled: true                    # bật/tắt toàn bộ plugin
disable_tnt_damage: true                # chặn sát thương TNT
disable_respawn_anchor_damage: true     # chặn sát thương Respawn Anchor
```

## Cài đặt

```bash
mvn clean package
```

Copy `target/DisableDamage-Kayji-1.0-SNAPSHOT.jar` vào thư mục `plugins/` rồi restart server.

> Maven Shade Plugin được dùng để đóng gói dependency vào jar (loại trừ `spigot-api`).

## Cấu trúc dự án

```
├── pom.xml                                     Maven + Shade (Java 16)
└── src/main
    ├── java/com/example/disabledamage
    │   └── DisableDamagePlugin.java            Lắng nghe EntityDamageEvent + xử lý lệnh
    └── resources
        ├── plugin.yml                          Lệnh, quyền, metadata
        └── config.yml                          3 công tắc bật/tắt
```

## Giấy phép

[GNU General Public License v3.0](LICENSE)
