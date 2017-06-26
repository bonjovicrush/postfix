package test1228;

import java.util.LinkedList;
import java.util.Scanner;

public class ToPostfix05 {

	static LinkedList<String> postfixQue = new LinkedList<String>();
	static LinkedList<String> opStack = new LinkedList<String>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		LinkedList<String> calQue = new LinkedList<String>();

		Scanner sc = new Scanner(System.in);
		String input;

		int queSize, stackSize;

		System.out.println("������ �Է��ϼ���: ");
		input = sc.next();
		String compare;
		boolean numberContinue = false;

		for (int i = 0; i < input.length(); i++) {
			compare = input.substring(i, i + 1); // i��° �Է°� �ޱ�

			// ������ �� ť�� ����
			if (compare.compareTo("0") >= 0 && compare.compareTo("9") <= 0) {
				userQue(i, compare, numberContinue);
				numberContinue = true;
			}
			// "(" �� �� ���ÿ� Ǫ��
			else if (compare.equals("(")) {
				System.out.println("(�� ���ÿ� Ǫ��");
				opStack.add(compare);
				numberContinue = false;
			}
			// ")" �� �� "("���� ������ ���� ���Ͽ� ť�� ����
			else if (compare.equals(")")) {
				while (opStack.isEmpty() != true) {
					System.out.println("��ȣ���� ������ ���ÿ��� ���� ť�� ����");
					postfixQue.offer(opStack.removeLast());
					if (opStack.getLast().equals("(")) {
						opStack.removeLast();
						break;
					}
				}
				numberContinue = false;
				// opStack.removeLast();
			}

			// ������ �� �� ���ÿ� Ǫ��
			else {
				userStack(i, compare);
				numberContinue = false;
			}

		}

		// ���ÿ� �����ִ� �����ڸ� ť�� ����
		stackSize = opStack.size();
		for (int i = 0; i < stackSize; i++) {
			System.out.println("������ �����ڸ� ť�� ����");
			postfixQue.offer(opStack.removeLast());
		}
		// ����� �� ���
		System.out.println("�����(��): ");
		// ����ϱ� ���� ť�� ����

		calQue = (LinkedList<String>) postfixQue.clone();

		queSize = postfixQue.size();
		for (int i = 0; i < queSize; i++) {
			System.out.print(postfixQue.poll() + " ");
		}

		// ����� ���
		System.out.println("\n�����(value): " + calculateQue(calQue));

	}

	/*-------------------------
	 * ������ �켱���� ���Լ�
	---------------------------*/
	public static int priorityCompare(String pArray) {
		String[][] pPriority = { { "(", "", "" }, { "+", "-", "" }, { "*", "/", "" }, { "r", "l", "^" } };
		int pArrayPriority = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				if (pArray.equals(pPriority[i][j])) {
					pArrayPriority = i;
				}
			}
		}
		return pArrayPriority;
	}

	public static double calculateQue(LinkedList<String> cQue) {
		LinkedList<String> calStack = new LinkedList<String>();
		String cCompare;
		double result = 0, cVal1, cVal2;
		int queSize = cQue.size();

		for (int i = 0; i < queSize; i++) {
			cCompare = cQue.poll();
			if (cCompare.compareTo("0") >= 0 && cCompare.compareTo("9") <= 0) {
				calStack.add(cCompare);
			} else {
				switch (cCompare) {
				case "+":
					cVal1 = Double.parseDouble(calStack.removeLast());
					cVal2 = Double.parseDouble(calStack.removeLast());
					result = cVal2 + cVal1;
					calStack.add(String.valueOf(result));
					break;
				case "-":
					cVal1 = Double.parseDouble(calStack.removeLast());
					cVal2 = Double.parseDouble(calStack.removeLast());
					result = cVal2 - cVal1;
					calStack.add(String.valueOf(result));
					break;
				case "*":
					cVal1 = Double.parseDouble(calStack.removeLast());
					cVal2 = Double.parseDouble(calStack.removeLast());
					result = cVal2 * cVal1;
					calStack.add(String.valueOf(result));
					break;
				case "/":
					cVal1 = Double.parseDouble(calStack.removeLast());
					cVal2 = Double.parseDouble(calStack.removeLast());
					result = cVal2 / cVal1;
					calStack.add(String.valueOf(result));
					break;
				case "l":
					cVal1 = Double.parseDouble(calStack.removeLast());
					result = Math.log10(cVal1);
					calStack.add(String.valueOf(result));
					break;
				case "r":
					cVal1 = Double.parseDouble(calStack.removeLast());
					result = Math.sqrt(cVal1);
					calStack.add(String.valueOf(result));
					break;
				case "^":
					cVal1 = Double.parseDouble(calStack.removeLast());
					cVal2 = Double.parseDouble(calStack.removeLast());
					result = Math.pow(cVal2, cVal1);
					calStack.add(String.valueOf(result));
					break;
				}
			}
		}

		return result;
	}

	public static void userStack(int i, String uCompare) {

		String opStack1, opStack2;

		if (opStack.isEmpty()) {
			opStack.add(uCompare);
		} else if (opStack.isEmpty() != true && priorityCompare(uCompare) > priorityCompare(opStack.getLast())) {
			System.out.println((i + 1) + "��° ���ڿ� ������: " + uCompare + "�� ���ÿ� push");
			opStack.add(uCompare);
		} else {
			while (opStack.isEmpty() != true && priorityCompare(uCompare) <= priorityCompare(opStack.getLast())) {
				System.out.println("������ �����ڸ� ť�� ����");
				postfixQue.offer(opStack.removeLast());
			}
			opStack.add(uCompare);
		}

	}

	public static void userQue(int i, String uCompare, boolean numberContinue) {
		// String opStack1, opStack2;
		System.out.println((i + 1) + "��° ���ڿ� ����: " + uCompare + "�� ť�� ����");
		int uNumber;

		if (numberContinue == true) {
			uNumber = Integer.parseInt(postfixQue.removeLast()) * 10 + Integer.parseInt(uCompare);
			postfixQue.offer(String.valueOf(uNumber));
		} else {
			postfixQue.offer(uCompare);
		}
	}

}
