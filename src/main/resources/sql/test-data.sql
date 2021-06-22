insert into singer (first_name, last_name, birth_date) values
('John', 'Mayer', '1977-10-16'),
('Eric', 'Clapton', '1945-03-30'),
('John', 'Butler', '1975-04-01');

insert into album (singer_id, title, release_date) values
(1, 'The Search For Everything', '2017-01-20'),
(1, 'Battle Studies', '2009-11-17'),
(2, 'From The Cradle ', '1994-09-13');


insert into instrument (instrument_id) values
('Guitar'),
('Piano'),
('Voice'),
('Drums'),
('Synthesizer');

insert into singer_instrument(singer_id, instrument_id) values
(1, 'Guitar'),
(1, 'Piano'),
(2, 'Guitar');