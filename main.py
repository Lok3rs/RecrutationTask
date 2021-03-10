# This task solved also with Java and Javascript you can find on my github -> github.com/Lok3rs/RecrutationTask
import math
import sys

max_elephants_number = 1000000


def check_minimum_strength_needed() -> int:
    elephants_weights = [None] * max_elephants_number
    base_elephants_order = [None] * max_elephants_number
    perm = [None] * max_elephants_number
    checked = [None] * max_elephants_number

    with sys.stdin as file:
        number_of_elephants = int(file.readline())
        elephants_weight = file.readline().split()
        base_order = file.readline().split()
        target_order = file.readline().split()

        for i in range(number_of_elephants):
            elephants_weights[i] = int(elephants_weight[i])
            # Subtracts 1 from base order integer to get valid index
            base_elephants_order[i] = int(base_order[i]) - 1
            target_order_index = int(target_order[i]) - 1
            perm[target_order_index] = base_elephants_order[i]
        # replaced all None array elements with infinity to avoid TypeError
        lightest_elephant_weight = min([weight if weight else math.inf for weight in elephants_weights])

        min_strength_needed = 0

        for i in range(number_of_elephants):
            if not checked[i]:
                min_cycle_weight = math.inf
                cycle_weights_sum = 0
                cur = i
                cycle_length = 0
                break_condition = False
                while not break_condition:
                    min_cycle_weight = min(min_cycle_weight, elephants_weights[cur])
                    cycle_weights_sum += elephants_weights[cur]
                    cur = perm[cur]
                    checked[cur] = True
                    cycle_length += 1
                    break_condition = (cur == i)
                min_strength_needed += min(
                    cycle_weights_sum+(cycle_length-2)*min_cycle_weight,
                    cycle_weights_sum+min_cycle_weight+(cycle_length+1)*lightest_elephant_weight)
        return min_strength_needed


if __name__ == "__main__":
    print(check_minimum_strength_needed())
