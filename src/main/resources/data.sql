-- 加 on conflict DO NOTHING;是为了重复启动的时候忽略id冲突

-- 插入功能

INSERT INTO common_function (id, command, created_at, description, function_id, last_modified_at, name, version)
VALUES ('2602b236-05f4-40df-a4ad-6feb5e91f16b', '开关', 1498647420511, NULL, 'cf001', 1498647420511, '开关', 0) on conflict DO NOTHING;

INSERT INTO common_function (id, command, created_at, description, function_id, last_modified_at, name, version)
VALUES ('8d789b1d-7e99-47b5-971f-8ce4517e44aa', '工作模式', 1498647420511, NULL, 'cf002', 1498647420511, '工作模式', 0) on conflict DO NOTHING;

INSERT INTO common_function (id, command, created_at, description, function_id, last_modified_at, name, version)
VALUES ('74217304-b92a-4395-8bee-11260cd66fbf', '开关', 1498647420513, NULL, 'cf021', 1498647420513, '开关', 0) on conflict DO NOTHING;

INSERT INTO common_function (id, command, created_at, description, function_id, last_modified_at, name, version)
VALUES ('95290536-54de-48ba-9a37-97254618ec91', '清扫模式', 1498647420513, NULL, 'cf022', 1498647420513, '清扫模式', 0) on conflict DO NOTHING;

-- 插入产品

INSERT INTO product_type (id, created_at, description, group_name, last_modified_at, name, version)
VALUES ('77616169-cc17-4051-8657-f706e7f4d303', 1498647420492, NULL, '大家电', 1498647420492, '冰箱', 0) on conflict DO NOTHING;

INSERT INTO product_type (id, created_at, description, group_name, last_modified_at, name, version)
VALUES ('be9b50f3-0e7e-4398-8d74-7cf55e66f282', 1498647420512, NULL, '小家电', 1498647420512, '扫地机器人', 0) on conflict DO NOTHING;

-- data definition id的关联表，由于没有主键约束，在启动时会重复插入数据，所以使用where子句跳过。

INSERT INTO product_type_data_ids (product_type_id, data_ids)
select '77616169-cc17-4051-8657-f706e7f4d303', '79f06fcb-c1f1-4791-a8b0-5f2043d39dc6'
WHERE NOT EXISTS (SELECT 1 FROM product_type_data_ids WHERE product_type_id = '77616169-cc17-4051-8657-f706e7f4d303' and data_ids = '79f06fcb-c1f1-4791-a8b0-5f2043d39dc6');

INSERT INTO product_type_data_ids (product_type_id, data_ids)
SELECT '77616169-cc17-4051-8657-f706e7f4d303', '0dd8c094-71b4-43c7-9bce-15adbfa63043'
WHERE not EXISTS (SELECT 1 FROM product_type_data_ids WHERE product_type_id = '77616169-cc17-4051-8657-f706e7f4d303' and data_ids = '0dd8c094-71b4-43c7-9bce-15adbfa63043');

INSERT INTO product_type_data_ids (product_type_id, data_ids)
SELECT '77616169-cc17-4051-8657-f706e7f4d303', 'abf88f5a-5b5f-4a34-85cc-471ba1a73d23'
WHERE not EXISTS (SELECT 1 FROM product_type_data_ids WHERE product_type_id = '77616169-cc17-4051-8657-f706e7f4d303' and data_ids = 'abf88f5a-5b5f-4a34-85cc-471ba1a73d23');

INSERT INTO product_type_data_ids (product_type_id, data_ids)
SELECT 'be9b50f3-0e7e-4398-8d74-7cf55e66f282', 'becf9c78-8bfd-4b0e-b6af-170351b1d993'
WHERE not EXISTS (SELECT 1 FROM product_type_data_ids WHERE product_type_id = 'be9b50f3-0e7e-4398-8d74-7cf55e66f282' and data_ids = 'becf9c78-8bfd-4b0e-b6af-170351b1d993');

INSERT INTO product_type_data_ids (product_type_id, data_ids)
SELECT 'be9b50f3-0e7e-4398-8d74-7cf55e66f282', 'a868479a-6331-4903-bcb4-774cd3522de9'
WHERE not EXISTS (SELECT 1 FROM product_type_data_ids WHERE product_type_id = 'be9b50f3-0e7e-4398-8d74-7cf55e66f282' and data_ids = 'a868479a-6331-4903-bcb4-774cd3522de9');

-- functions 的关联表，由于没有主键约束，在启动时会重复插入数据，所以使用where子句跳过。
INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '77616169-cc17-4051-8657-f706e7f4d303', '2602b236-05f4-40df-a4ad-6feb5e91f16b'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '77616169-cc17-4051-8657-f706e7f4d303' and functions_id = '2602b236-05f4-40df-a4ad-6feb5e91f16b');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '77616169-cc17-4051-8657-f706e7f4d303', '8d789b1d-7e99-47b5-971f-8ce4517e44aa'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '77616169-cc17-4051-8657-f706e7f4d303' and functions_id = '8d789b1d-7e99-47b5-971f-8ce4517e44aa');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT 'be9b50f3-0e7e-4398-8d74-7cf55e66f282', '74217304-b92a-4395-8bee-11260cd66fbf'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = 'be9b50f3-0e7e-4398-8d74-7cf55e66f282' and functions_id = '74217304-b92a-4395-8bee-11260cd66fbf');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT 'be9b50f3-0e7e-4398-8d74-7cf55e66f282', '95290536-54de-48ba-9a37-97254618ec91'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = 'be9b50f3-0e7e-4398-8d74-7cf55e66f282' and functions_id = '95290536-54de-48ba-9a37-97254618ec91');