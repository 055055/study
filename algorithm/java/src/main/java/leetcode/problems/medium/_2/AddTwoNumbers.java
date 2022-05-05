package leetcode.problems.medium._2;

public class AddTwoNumbers {

    public static void main(String[] args) {

        ListNode list1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode list2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode listNode = addTwoNumbers(list1, list2);
        while (listNode != null) {
            System.out.println("listNode = " + listNode.val);
            listNode = listNode.next;

        }
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int sum = 0;
        ListNode resultList = new ListNode();
        ListNode head = resultList;

        while ((l1 != null) || (l2 != null)) {
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;

            }

            resultList.next = new ListNode(sum % 10);
            sum /= 10;
            resultList = resultList.next;
        }

        if ( sum > 0) resultList.next = new ListNode(sum);
        return head.next;
    }
}
