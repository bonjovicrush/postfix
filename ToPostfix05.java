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

		System.out.println("수식을 입력하세요: ");
		input = sc.next();
		String compare;
		boolean numberContinue = false;

		for (int i = 0; i < input.length(); i++) {
			compare = input.substring(i, i + 1); // i번째 입력값 받기

			// 숫자일 때 큐에 저장
			if (compare.compareTo("0") >= 0 && compare.compareTo("9") <= 0) {
				userQue(i, compare, numberContinue);
				numberContinue = true;
			}
			// "(" 일 때 스택에 푸쉬
			else if (compare.equals("(")) {
				System.out.println("(를 스택에 푸쉬");
				opStack.add(compare);
				numberContinue = false;
			}
			// ")" 일 때 "("나올 때까지 스택 팝하여 큐로 삽입
			else if (compare.equals(")")) {
				while (opStack.isEmpty() != true) {
					System.out.println("괄호안의 연산을 스택에서 꺼내 큐로 삽입");
					postfixQue.offer(opStack.removeLast());
					if (opStack.getLast().equals("(")) {
						opStack.removeLast();
						break;
					}
				}
				numberContinue = false;
				// opStack.removeLast();
			}

			// 연산자 일 때 스택에 푸쉬
			else {
				userStack(i, compare);
				numberContinue = false;
			}

		}

		// 스택에 남아있는 연산자를 큐에 삽입
		stackSize = opStack.size();
		for (int i = 0; i < stackSize; i++) {
			System.out.println("스택의 연산자를 큐에 삽입");
			postfixQue.offer(opStack.removeLast());
		}
		// 결과값 식 출력
		System.out.println("결과값(식): ");
		// 계산하기 위해 큐값 복사

		calQue = (LinkedList<String>) postfixQue.clone();

		queSize = postfixQue.size();
		for (int i = 0; i < queSize; i++) {
			System.out.print(postfixQue.poll() + " ");
		}

		// 결과값 출력
		System.out.println("\n결과값(value): " + calculateQue(calQue));

	}

	/*-------------------------
	 * 연산자 우선순위 비교함수
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
			System.out.println((i + 1) + "번째 문자열 연산자: " + uCompare + "를 스택에 push");
			opStack.add(uCompare);
		} else {
			while (opStack.isEmpty() != true && priorityCompare(uCompare) <= priorityCompare(opStack.getLast())) {
				System.out.println("스택의 연산자를 큐에 삽입");
				postfixQue.offer(opStack.removeLast());
			}
			opStack.add(uCompare);
		}

	}

	public static void userQue(int i, String uCompare, boolean numberContinue) {
		// String opStack1, opStack2;
		System.out.println((i + 1) + "번째 문자열 숫자: " + uCompare + "를 큐에 삽입");
		int uNumber;

		if (numberContinue == true) {
			uNumber = Integer.parseInt(postfixQue.removeLast()) * 10 + Integer.parseInt(uCompare);
			postfixQue.offer(String.valueOf(uNumber));
		} else {
			postfixQue.offer(uCompare);
		}
	}

}
