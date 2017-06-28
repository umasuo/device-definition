-- 加 on conflict DO NOTHING;是为了重复启动的时候忽略id冲突

INSERT INTO common_function (id, command, created_at, description, function_id, last_modified_at, name, version) VALUES ('2602b236-05f4-40df-a4ad-6feb5e91f16b', '开关', 1498647420511, NULL, 'cf001', 1498647420511, '开关', 0) on conflict DO NOTHING;
INSERT INTO common_function (id, command, created_at, description, function_id, last_modified_at, name, version) VALUES ('8d789b1d-7e99-47b5-971f-8ce4517e44aa', '工作模式', 1498647420511, NULL, 'cf002', 1498647420511, '工作模式', 0) on conflict DO NOTHING;
INSERT INTO common_function (id, command, created_at, description, function_id, last_modified_at, name, version) VALUES ('74217304-b92a-4395-8bee-11260cd66fbf', '开关', 1498647420513, NULL, 'cf021', 1498647420513, '开关', 0) on conflict DO NOTHING;
INSERT INTO common_function (id, command, created_at, description, function_id, last_modified_at, name, version) VALUES ('95290536-54de-48ba-9a37-97254618ec91', '清扫模式', 1498647420513, NULL, 'cf022', 1498647420513, '清扫模式', 0) on conflict DO NOTHING;

INSERT INTO product_type (id, created_at, description, group_name, last_modified_at, name, version) VALUES ('77616169-cc17-4051-8657-f706e7f4d303', 1498647420492, NULL, '大家电', 1498647420492, '冰箱', 0) on conflict DO NOTHING;
INSERT INTO product_type (id, created_at, description, group_name, last_modified_at, name, version) VALUES ('be9b50f3-0e7e-4398-8d74-7cf55e66f282', 1498647420512, NULL, '小家电', 1498647420512, '扫地机器人', 0) on conflict DO NOTHING;

INSERT INTO product_type_data_ids (product_type_id, data_ids) VALUES ('77616169-cc17-4051-8657-f706e7f4d303', '79f06fcb-c1f1-4791-a8b0-5f2043d39dc6') on conflict DO NOTHING;
INSERT INTO product_type_data_ids (product_type_id, data_ids) VALUES ('77616169-cc17-4051-8657-f706e7f4d303', '0dd8c094-71b4-43c7-9bce-15adbfa63043') on conflict DO NOTHING;
INSERT INTO product_type_data_ids (product_type_id, data_ids) VALUES ('77616169-cc17-4051-8657-f706e7f4d303', 'abf88f5a-5b5f-4a34-85cc-471ba1a73d23') on conflict DO NOTHING;

INSERT INTO product_type_functions (product_type_id, functions_id) VALUES ('77616169-cc17-4051-8657-f706e7f4d303', '2602b236-05f4-40df-a4ad-6feb5e91f16b') on conflict DO NOTHING;
INSERT INTO product_type_functions (product_type_id, functions_id) VALUES ('77616169-cc17-4051-8657-f706e7f4d303', '8d789b1d-7e99-47b5-971f-8ce4517e44aa') on conflict DO NOTHING;
INSERT INTO product_type_functions (product_type_id, functions_id) VALUES ('be9b50f3-0e7e-4398-8d74-7cf55e66f282', '74217304-b92a-4395-8bee-11260cd66fbf') on conflict DO NOTHING;
INSERT INTO product_type_functions (product_type_id, functions_id) VALUES ('be9b50f3-0e7e-4398-8d74-7cf55e66f282', '95290536-54de-48ba-9a37-97254618ec91') on conflict DO NOTHING;