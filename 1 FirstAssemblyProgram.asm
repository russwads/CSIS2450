# ***NUMBER$ in Assembly***
# (c) MMXX by Russell Wadsworth
# SLCC CSIS 2810--Computer Architecture
# Calculates the sum of three numbers entered by the user; also, finds the smallest and largest numbers to display.

# Registers used
#	$t0 holds the sum of the three numbers
#	$t1 holds the first number
#	$t2 holds the second number	
#	$t3 holds the third number
#	$t4 holds the iteration count
#	$s0 holds the smallest number to aid in finding the largest number
#	$a0 holds input and output values for Syscall operations
#	$v0 holds syscall codes and input values.

.text	#begin text segment
	.globl	main	#program entry point
	
main:	#main program
	li	$t4, 1		#initializes iteration count
loop:	
	bgt 	$t4, 2, brexit	#if program completes twice, branch to exit code (i > 2 == brexit)
# ********************************************************
# Enter three integers from the keyboard
# ********************************************************
	# First Integer
	la	$a0, promp1	#string "promp1" to display
	li	$v0, 4		#load print_screen code
	syscall			#display string
	
	la	$a0, nd		#string "endl" to display
	li	$v0, 4		#load print_screen code
	syscall			#display string
	
	li	$v0, 5		#load read_int code
	syscall			#read keyboard
	move	$t1, $v0	#move integer to temp register
#---------------------------------------------------------------
	# Second Integer
	la	$a0, promp2	#string "promp2" to display
	li	$v0, 4		#load print_screen code
	syscall			#display string
	
	la	$a0, nd		#string "endl" to display
	li	$v0, 4		#load print_screen code
	syscall			#display string
	
	li	$v0, 5		#load read_int code
	syscall			#read keyboard
	move	$t2, $v0	#move integer to temp register
#---------------------------------------------------------------
	# Third Integer
	la	$a0, promp3	#string "promp3" to display
	li	$v0, 4		#load print_screen code
	syscall			#display string
	
	la	$a0, nd		#string "endl" to display
	li	$v0, 4		#load print_screen code
	syscall			#display string
	
	li	$v0, 5		#load read_int code
	syscall			#read keyboard
	move	$t3, $v0	#move integer to temp register
	
# ********************************************************
# Calculate and display the sum of the numbers
# ********************************************************
	# Add numbers
	add	$t0, $t1, $t2	#First two integers--sum is placed in $t0
	add	$t0, $t0, $t3	#Adding third integer--sum is placed in $t0
	
	# Display sum
	la	$a0, ans1	#string "ans1" to display
	li	$v0, 4		#load print_screen code
	syscall			#display string
	
	move 	$a0, $t0	#sum integer to display
	li 	$v0, 1		#load print_integer code
	syscall			#display integer
	
# ********************************************************
# Select and display the smallest of the numbers
# ********************************************************
	la	$a0, ans2	#string "ans2" to display
	li	$v0, 4		#load print_screen code
	syscall			#display string
	
	blt 	$t1, $t2, int1	#if first number is less than second, jump to L1
 	blt 	$t2, $t1, int3	#if second number is less than first, jump to L3

	int1:
	blt 	$t1, $t3, int2	#if first number is less than third, jump to L2
	blt 	$t3, $t1, int3	#if third number is less than first, jump to L3

	int2:
	add 	$s0, $t1, $zero	#first number is smallest: move first to $s0 to save steps to find larger number
	move	$a0, $t1	#also, move first to $a0
	li 	$v0, 1		#load print_integer code
	syscall			#display integer
	j	largenum	#jump to find larger number

	int3:
	blt 	$t2, $t3, int4	#if second number is less than third, jump to L4
	blt 	$t3, $t2, int5	#if third number is less than second, jump to L5

	int4:
	add 	$s0, $t2, $zero	#second number is smallest: move second to $s0 to save steps to find larger number
	move	$a0, $t2	#also, move second to $a0
	li 	$v0, 1		#load print_integer code
	syscall			#display integer
	j	largenum	#jump to find larger number

	int5:
	add 	$s0, $t3, $zero	#third number is smallest: 
	move 	$a0, $t3	#also, move third to $a0
	li 	$v0, 1		#load print_integer code
	syscall			#display integer
	j	largenum	#jump to find larger number

	
# ********************************************************
# Select and display the largest of the numbers
# ********************************************************
	largenum:
	la	$a0, ans3	#string "ans3" to display
	li	$v0, 4		#load print_screen code
	syscall			#display string
	
	beq	$s0, $t1, jnt1	#if first is smallest, jump to L1
	beq	$s0, $t2, jnt2	#if second is smallest, jump to L2
	beq	$s0, $t3, jnt3	#if third is smallest, jump to L3
	
	jnt1:
	blt	$t2, $t3, trd	#if second is less than third, third is largest: jump to trd
	blt	$t3, $t2, snd	#if third is less than second, second is largest: jump to snd
	
	jnt2:
	blt	$t1, $t3, trd	#if first is less than third, third is largest: jump to trd
	blt	$t3, $t1, fst	#if third is less than first, first is largest: jump to fst
	
	jnt3:
	blt	$t1, $t2, snd	#if first is less than second, second is largest: jump to snd
	blt	$t2, $t1, fst	#if second is less than first, first is largest: jump to fst
	
	fst:
	move	$a0, $t1	#also, move first to $a0
	li 	$v0, 1		#load print_integer code
	syscall			#display integer
	j	continuations	#jump to begin loop iteration
	
	snd:
	move	$a0, $t2	#also, move second to $a0
	li 	$v0, 1		#load print_integer code
	syscall			#display integer
	j	continuations	#jump to begin loop iteration
	
	trd:
	move 	$a0, $t3	#also, move third to $a0
	li 	$v0, 1		#load print_integer code
	syscall			#display integer
	j	continuations	#jump to begin loop iteration
	
# ********************************************************
# Allow for multiple iterations of the same program
# ********************************************************
	continuations:
	la	$a0, nd		#string "endl" to display
	li	$v0, 4		#load print_screen code
	syscall			#display string
	
	addi	$t4, $t4, 1	#adds iteration to program
	j	loop		#jumps to beginning of loop

brexit:	#represents issues with the European Union that benefits small businesses,
	#or the exit code for this program. Either way... :)
	la	$a0, hold	#hold screen until key is pressed
	li	$v0, 4		#load print_screen code
	syscall			#display string
	
	li	$v0, 12		#load input code
	syscall			#hold the screen for input

	li	$v0, 10		#load exit code
	syscall			#exit program

.data	#define data segment
	promp1:	.asciiz	"Please enter your first number: "
	promp2:	.asciiz	"Please enter your second number: "
	promp3:	.asciiz	"Please enter your final number: "
	ans1:	.asciiz	"The sum of the three numbers is "
	ans2:	.asciiz	"\nThe smallest of the three numbers is "
	ans3:	.asciiz	"\nThe largest of the three numbers is "
	nd:	.asciiz	"\n"
	hold:   .asciiz "Press any key to continue..."
#end of program
