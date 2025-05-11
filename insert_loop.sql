INSERT INTO track_login (os, app_version, app_id, target_id, screen_size, login_from, created_at)
SELECT 
    CASE WHEN random() < 0.5 THEN 'IOS' ELSE 'ANDROID' END,
    CASE WHEN random() < 0.5 THEN '2.9.8' ELSE '3.0.0' END,
    'app123',
    floor(random() * 280000)::int + 1,
    '1080x1920',
    'USER',
    NOW()
FROM generate_series(1, 10);