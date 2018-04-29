-- Author: DVT HDH

SET NAMES utf8mb4;


INSERT INTO `word` (`english`, `welsh`, `welsh_gender`)
VALUES
	('man', 'dyn', 'masculine'),
	('father', 'tad', 'masculine'),
	('brother', 'brawd', 'masculine'),
	('uncle', 'ewythr', 'masculine'),
	('bull', 'tarw', 'masculine'),
	('horse', 'ceffyl', 'masculine'),
	('rooster', 'ceiliog', 'masculine'),
	('ram', 'maharen', 'masculine'),
	('dog', 'ci', 'masculine'),
	('son', 'mab', 'masculine'),
	('grandfather', 'tad-cu', 'masculine'),
	('christmas', 'y nadolig', 'masculine'),
	('easter', 'y pasg', 'masculine'),
	('rain', 'glaw', 'masculine'),
	('snow', 'eira', 'masculine'),
	('fire', 'tan', 'masculine'),
	('coffee', 'coffi', 'masculine'),
	('beer', 'cwrw', 'masculine'),
	('husband', 'gwr', 'masculine'),
	('woman', 'dynes', 'feminine'),
	('daughter', 'merch', 'feminine'),
	('girl', 'ferch', 'feminine'),
	('mother', 'mam', 'feminine'),
	('cow', 'buwch', 'feminine'),
	('sister', 'chwaer', 'feminine'),
	('mare', 'caseg', 'feminine'),
	('ewe', 'mamog', 'feminine'),
	('hen', 'iar', 'feminine'),
	('army', 'byddin', 'feminine'),
	('wife', 'gwraig', 'feminine'),
	('feminine teacher', 'athrawes', 'feminine'),
	('easter', 'y pasg', 'feminine'),
	('oak tree', 'derwen', 'feminine'),
	('birch tree', 'bedwen', 'feminine'),
	('snowden', 'yr wyddfa', 'feminine'),
	('dress', 'ffrog', 'feminine'),
	('glove', 'maneg', 'feminine'),
	('nile river', 'nil', 'feminine');


INSERT INTO `user` (`username`, `password`, `role`)
VALUES
	('administrator', '$2a$10$Or/.9dQ4PMurdxsrUVr2WOwSGAcYGxoiSFghsdIYCTBMblWZEQdja', 'administrator'),
	('instructor', '$2a$10$J22XoJbIIUpe8CIOCNQKFelDqUEEkkZlDNek7PE7uc7nEFct1Tx1S', 'instructor'),
	('student', '$2a$10$pbK5VwodsMd.E9XastH8fupiO4Zi63ehT.bHs1ik7NCZarDESmmxW', 'student');
