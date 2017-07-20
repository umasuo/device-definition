-- 加 on conflict DO NOTHING;是为了重复启动的时候忽略id冲突

-- 插入功能

INSERT INTO common_function (id, command, created_at, data_type, description, function_id, last_modified_at, name, transfer_type, version)
VALUES ('2602b236-05f4-40df-a4ad-6feb5e91f16b', '开关', 1498647420511, '{"type": "boolean"}', NULL, 'cf001', 1499342216822, '开关', 0, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, command, created_at, data_type, description, function_id, last_modified_at, name, transfer_type, version)
VALUES ('8d789b1d-7e99-47b5-971f-8ce4517e44aa', '工作模式', 1498647420511, '{"type": "enum", "values": ["level1", "level2", "level3"]}', NULL, 'cf002', 1499342216826, '工作模式', 2, 1)
on conflict DO NOTHING;

INSERT INTO common_function (id, command, created_at, data_type, description, function_id, last_modified_at, name, transfer_type, version)
VALUES ('74217304-b92a-4395-8bee-11260cd66fbf', '开关', 1498647420513, '{"type": "value", "endValue": 100, "interval": 2, "multiple": 4, "startValue": 1}', NULL, 'cf021', 1499342216827, '开关', 0, 1)
on conflict DO NOTHING;

INSERT INTO common_function (id, command, created_at, data_type, description, function_id, last_modified_at, name, transfer_type, version)
VALUES ('95290536-54de-48ba-9a37-97254618ec91', '清扫模式', 1498647420513, '{"type": "string"}', NULL, 'cf022', 1499342216827, '清扫模式', 2, 1) on conflict DO NOTHING;

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

-- transfer_type: 0 - 可上可下；1 － 上；2 － 下

-- 基本指令

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('1', 1498647420511, '{"type": "boolean"}', '1', 1499342216822, '开关', 0, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('2', 1498647420511, '{"type": "value", "endValue": 10000000000000, "interval": 1, "multiple": 1, "startValue": 0}', '2', 1499342216822, '同步时间', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('3', 1498647420511, '{"type": "value", "endValue": 10000000000000, "interval": 1, "multiple": 1, "startValue": 0}', '3', 1499342216822, '倒计时', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('4', 1498647420511, '{"type": "string"}', '4', 1499342216822, '状态查询', 2, 1) on conflict DO NOTHING;

-- 插座的指令

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('1001', 1498647420511, '{"type": "boolean"}', '1', 1499342216822, '开关', 0, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('1002', 1498647420511, '{"type": "value", "endValue": 10000000000000, "interval": 1, "multiple": 1, "startValue": 0}', '2', 1499342216822, '同步时间', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('1003', 1498647420511, '{"type": "value", "endValue": 10000000000000, "interval": 1, "multiple": 1, "startValue": 0}', '3', 1499342216822, '倒计时', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('1004', 1498647420511, '{"type": "string"}', '4', 1499342216822, '状态查询', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('101', 1498647420511, '{"type": "boolean"}', '101', 1499342216822, '插孔1', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('102', 1498647420511, '{"type": "boolean"}', '102', 1499342216822, '插孔2', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('103', 1498647420511, '{"type": "boolean"}', '103', 1499342216822, '插孔3', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('104', 1498647420511, '{"type": "boolean"}', '104', 1499342216822, '插孔4', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('105', 1498647420511, '{"type": "boolean"}', '105', 1499342216822, 'USB1', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('106', 1498647420511, '{"type": "boolean"}', '106', 1499342216822, 'USB2', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('107', 1498647420511, '{"type": "boolean"}', '107', 1499342216822, 'USB3', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('108', 1498647420511, '{"type": "boolean"}', '108', 1499342216822, 'USB4', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('109', 1498647420511, '{"type": "string"}', '109', 1499342216822, '状态上报', 1, 1) on conflict DO NOTHING;

-- 开关的指令

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('2001', 1498647420511, '{"type": "boolean"}', '1', 1499342216822, '开关', 0, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('2002', 1498647420511, '{"type": "value", "endValue": 10000000000000, "interval": 1, "multiple": 1, "startValue": 0}', '2', 1499342216822, '同步时间', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('2003', 1498647420511, '{"type": "value", "endValue": 10000000000000, "interval": 1, "multiple": 1, "startValue": 0}', '3', 1499342216822, '倒计时', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('2004', 1498647420511, '{"type": "string"}', '4', 1499342216822, '状态查询', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('201', 1498647420511, '{"type": "boolean"}', '201', 1499342216822, '开关1', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('202', 1498647420511, '{"type": "boolean"}', '202', 1499342216822, '开关2', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('203', 1498647420511, '{"type": "boolean"}', '203', 1499342216822, '开关3', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('204', 1498647420511, '{"type": "boolean"}', '204', 1499342216822, '开关4', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('205', 1498647420511, '{"type": "string"}', '205', 1499342216822, '状态上报', 1, 1) on conflict DO NOTHING;

-- 照明的指令

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('3001', 1498647420511, '{"type": "boolean"}', '1', 1499342216822, '开关', 0, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('3002', 1498647420511, '{"type": "value", "endValue": 10000000000000, "interval": 1, "multiple": 1, "startValue": 0}', '2', 1499342216822, '同步时间', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('3003', 1498647420511, '{"type": "value", "endValue": 10000000000000, "interval": 1, "multiple": 1, "startValue": 0}', '3', 1499342216822, '倒计时', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('3004', 1498647420511, '{"type": "string"}', '4', 1499342216822, '状态查询', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('301', 1498647420511, '{"type": "value", "endValue": 100, "interval": 1, "multiple": 1, "startValue": 0}', '301', 1499342216822, '亮度', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('302', 1498647420511, '{"type": "boolean"}', '302', 1499342216822, '冷暖', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('303', 1498647420511, '{"type": "enum", "values": ["柔光模式", "缤纷模式", "炫彩模式", "斑斓模式"]}', '303', 1499342216822, '模式', 2, 1) on conflict DO NOTHING;

INSERT INTO common_function (id, created_at, data_type, function_id, last_modified_at, name, transfer_type, version)
VALUES ('304', 1498647420511, '{"type": "string"}', '304', 1499342216822, '状态上报', 1, 1) on conflict DO NOTHING;

-- 产品类型

INSERT INTO product_type (id, created_at, description, group_name, last_modified_at, name, version)
VALUES ('10000', 1498647420492, NULL, '电工照明', 1498647420492, '插座', 0) on conflict DO NOTHING;

INSERT INTO product_type (id, created_at, description, group_name, last_modified_at, name, version)
VALUES ('10001', 1498647420492, NULL, '电工照明', 1498647420492, '开关', 0) on conflict DO NOTHING;

INSERT INTO product_type (id, created_at, description, group_name, last_modified_at, name, version)
VALUES ('10002', 1498647420492, NULL, '电工照明', 1498647420492, '照明', 0) on conflict DO NOTHING;

-- 关联产品类型与其指令

-- 插座与其指令

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10000', '1001'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10000' and functions_id = '1001');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10000', '1002'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10000' and functions_id = '1002');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10000', '1003'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10000' and functions_id = '1003');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10000', '1004'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10000' and functions_id = '1004');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10000', '101'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10000' and functions_id = '101');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10000', '102'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10000' and functions_id = '102');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10000', '103'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10000' and functions_id = '103');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10000', '104'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10000' and functions_id = '104');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10000', '105'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10000' and functions_id = '105');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10000', '106'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10000' and functions_id = '106');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10000', '107'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10000' and functions_id = '107');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10000', '108'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10000' and functions_id = '108');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10000', '109'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10000' and functions_id = '109');



-- 开关与其指令

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10001', '2001'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10001' and functions_id = '2001');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10001', '2002'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10001' and functions_id = '2002');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10001', '2003'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10001' and functions_id = '2003');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10001', '2004'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10001' and functions_id = '2004');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10001', '201'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10001' and functions_id = '201');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10001', '202'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10001' and functions_id = '202');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10001', '203'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10001' and functions_id = '203');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10001', '204'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10001' and functions_id = '204');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10001', '205'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10001' and functions_id = '205');

-- 照明与其指令

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10002', '3001'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10002' and functions_id = '3001');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10002', '3002'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10002' and functions_id = '3002');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10002', '3003'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10002' and functions_id = '3003');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10002', '3004'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10002' and functions_id = '3004');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10002', '301'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10002' and functions_id = '301');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10002', '302'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10002' and functions_id = '302');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10002', '303'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10002' and functions_id = '303');

INSERT INTO product_type_functions (product_type_id, functions_id)
SELECT '10002', '304'
WHERE not EXISTS (SELECT 1 FROM product_type_functions WHERE product_type_id = '10002' and functions_id = '304');