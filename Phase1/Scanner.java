package scanner;

/**
 * Authors: Krishna Foster, Nathan Garretson, Matthew Naugle, Ian Schiedenhelm
 * Reviewers: Omar Ndiaye, Wyatt Rock
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScannerPhase {

	private static final int[][] transitionTable = {
			// START
			// ! ( ) * + - . / 0 1 2 3 4
			{ 34, 30, 31, 28, 26, 27, -1, 29, 24, 24, 24, 24, 24,
					// 5 6 7 8 9 < = > A B C D E
					24, 24, 24, 24, 24, 36, 32, 38, 1, 1, 1, 1, 1,
					// F G H I J K L M N O P Q R
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					// S T U V W X Y Z a b c d e
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8,
					// f g h i j k l m n o p q r
					12, 1, 1, 15, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					// s t u v w x y z
					1, 1, 1, 1, 19, 1, 1, 1 },
			// IDENTIFIER
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// D
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// DO
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 4, 1, 1, 1, 1, 1 },
			// DOU
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// DOUB
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// DOUBL
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// DOUBLE 7
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// E
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// EL
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					10, 1, 1, 1, 1, 1, 1, 1 },
			// ELS
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// ELSE 11
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// F
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 13, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// FO
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// FOR 14
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// I 15
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 16, 1, 1, 1, 1, 1, 1, 1, 17, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// IF 16
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// IN 17
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 18, 1, 1, 1, 1, 1, 1 },
			// INT 18
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// W
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// WH
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 21, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// WHI
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 22, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// WHIL
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 23, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// WHILE 23
			{ -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1 },
			// INTEGER
			{ -1, -1, -1, -1, -1, -1, 25, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// FLOAT
			{ -1, -1, -1, -1, -1, -1, -1, -1, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// ADD_OP
			{ -1, -1, -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// SUB_OP
			{ -1, -1, -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// MUL_OP
			{ -1, -1, -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// DIV_OP
			{ -1, -1, -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// O_PAREN
			{ -1, -1, -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// C_PAREN
			{ -1, -1, -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// ASSIGN
			{ -1, -1, -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, -1, 32, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// EQUAL
			{ -1, -1, -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// NOT
			{ -1, -1, -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 36, 35, 38, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// NOT_EQUAL
			{ -1, -1, -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// LESSER
			{ -1, -1, -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, -1, 37, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// LESSER_EQUAL
			{ -1, -1, -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// GREATER
			{ -1, -1, -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, -1, 39, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 },
			// GREATER_EQUAL
			{ -1, -1, -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 8, 12, 1, 1, 15, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1, 1, 1, 19, 1, 1, 1 }, };

	private static final List<Character> alphabet = List.of('!', '(', ')', '*', '+', '-', '.', '/', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '<', '=', '>', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

	public static List<Token> scan(String input) {
		// List to keep the tokens in
		List<Token> tokens = new ArrayList<>();
		// the current state the machine is at
		int state = 0;
		StringBuilder tokenValue = new StringBuilder();

		// Loop through every character in the input
		for (int i = 0; i < input.length(); i++) {
			char currentChar = input.charAt(i);
			int alphabetIndex = alphabet.indexOf(currentChar);

			if (Character.isWhitespace(currentChar)) {
				if (tokenValue.length() > 0) {
					Token.TokenClass tokenClass = classifyToken(state);
					tokens.add(new Token(tokenClass, tokenValue.toString()));
					state = 0;
					tokenValue.setLength(0);
				}
				continue;
			}

			// if current char isn't in our alphabet of chars, crash
			if (alphabetIndex == -1) {
				tokens.add(new Token(Token.TokenClass.UNKNOWN, String.valueOf(currentChar)));
				return tokens;
			}

			int newState = transitionTable[state][alphabetIndex];

			// if not in a valid state, crash
			if (newState == -1) {
				// check to see if state we were previously in was an identifier or number
				if (state == 1 || state == 20 || state == 24 || state == 25) {
					// classify previous token
					Token.TokenClass tokenClass = classifyToken(state);
					// add it to queue of tokens
					tokens.add(new Token(tokenClass, tokenValue.toString()));
					tokenValue.setLength(0); // Reset String builder
					state = 0; // Reset to start state
					// check the state of the char we are on
					newState = transitionTable[state][alphabetIndex];
					// if it's an accepting state
					if (isAcceptingState(newState)) {
						// add the char to the StringBuilder
						tokenValue.append(currentChar);
						// check to see if we have a operator with 2 chars
						if (isAcceptingState(newState) && i + 1 < input.length()
								&& alphabet.indexOf(input.charAt(i + 1)) != -1
								&& isAcceptingState(transitionTable[state][alphabet.indexOf(input.charAt(i + 1))])) {
							// if true, append nextChar onto tokenValue
							tokenValue.append(input.charAt(i + 1));
							// set state to the state we looked ahead at
							state = transitionTable[state][alphabet.indexOf(input.charAt(i + 1))];
							// increment i to skip over one we looked at
							i++;
						}
						// either way, classify and add to tokens queue
						tokenClass = classifyToken(newState);
						tokens.add(new Token(tokenClass, tokenValue.toString()));

						// Reset the token buffer and state for the next token
						tokenValue.setLength(0);
						state = 0; // Reset to start state
					}
				} else {
					// unknown token, crash
					tokenValue.append(currentChar);
					tokens.add(new Token(Token.TokenClass.UNKNOWN, tokenValue.toString()));
					return tokens;
				}

			} else {
				state = newState;
				tokenValue.append(currentChar);

				// if current state is accepting state AND i + 1 is less than input size
				// AND the next char is in alphabet list (not equal -1)
				// AND check one ahead to see if combo of current state and i+1 input make a
				// accepting state
				if (isAcceptingState(state) && i + 1 < input.length() && alphabet.indexOf(input.charAt(i + 1)) != -1
						&& isAcceptingState(transitionTable[state][alphabet.indexOf(input.charAt(i + 1))])) {
					// if true, append nextChar onto tokenValue
					tokenValue.append(input.charAt(i + 1));
					// set state to the state we looked ahead at
					state = transitionTable[state][alphabet.indexOf(input.charAt(i + 1))];
					// increment i to skip over one we looked at
					i++;
				}

				// If we have reached an accepting (final) state, emit a token
				if (isAcceptingState(state)) {

					Token.TokenClass tokenClass = classifyToken(state);
					tokens.add(new Token(tokenClass, tokenValue.toString()));

					// Reset the token buffer and state for the next token
					tokenValue.setLength(0);
					state = 0; // Reset to start state
				}
			}
		}

		// Handle the case where the input ends and there is still a token in progress
		if (tokenValue.length() > 0)

		{
			Token.TokenClass tokenClass = classifyToken(state);
			tokens.add(new Token(tokenClass, tokenValue.toString()));
		}

		return tokens;
	}

	// Helper function to check if a state is an accepting state
	private static boolean isAcceptingState(int state) {
		return state == 7 || state == 11 || state == 14 || state == 16 || state == 18 || state == 23 || state == 26
				|| state == 27 || state == 28 || state == 29 || state == 30 || state == 31 || state == 32 || state == 33
				|| state == 35 || state == 36 || state == 37 || state == 38 || state == 39;
	}

	private static Token.TokenClass classifyToken(int state) {
		if (state == 1) {
			return Token.TokenClass.IDENTIFIER;
		} else if (state == 24 || state == 25) {
			return Token.TokenClass.LITERAL;
		} else if (state >= 26 && state <= 39) {
			return Token.TokenClass.OPERATOR;
		} else if (state == 7 || state == 11 || state == 14 || state == 16 || state == 18 || state == 23) {
			return Token.TokenClass.KEYWORD;
		} else {
			return Token.TokenClass.IDENTIFIER;
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
//		System.out.println("Enter a string to scan: ");
//		String input = scanner.nextLine();
		String input = "for 	while \ndouble   <=int         var 1234 \nttt 4055.23";
		String invalid = "if else tests $$ while";
		String input2 = "if (double == int) variable = 2";

		List<Token> tokens = scan(input2);

		for (Token token : tokens) {
			System.out.println(token);
		}

		scanner.close();
	}
}