Problem Introduction
Given a sequence 𝑎1, . . . , 𝑎𝑛 of integers and an integer 𝑚 ≤ 𝑛, find the maximum among {𝑎𝑖
, . . . , 𝑎𝑖+𝑚−1} for
every 1 ≤ 𝑖 ≤ 𝑛 − 𝑚 + 1. A naive 𝑂(𝑛𝑚) algorithm for solving this problem scans each window separately.
Your goal is to design an 𝑂(𝑛) algorithm.
Problem Description
Input Format. The first line contains an integer 𝑛, the second line contains 𝑛 integers 𝑎1, . . . , 𝑎𝑛 separated
by spaces, the third line contains an integer 𝑚