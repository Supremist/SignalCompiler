.386
ASSUME CS:<identifier>CODE,DS:<identifier>DATA

<identifier>DATA SEGMENT
  addr dd ?
  addrw dw ?
  extern procname@<params_size>:far
<identifier>DATA ENDS

<identifier>CODE SEGMENT
org 100h
START:

<identifier>CODE ENDS
END START



.386
ASSUME CS:<identifier>CODE,DS:<identifier>DATA

<identifier>DATA SEGMENT
  addr dd ?
  addrw dw ?
  public procname
<identifier>DATA ENDS

<identifier>CODE SEGMENT
org 100h

procname PROC
push ebp
mov ebp, esp
pushad

@param2 equ [bp+8]
@param1 equ [bp+12]
@param0 equ [bp+16]

popad
pop ebp
ret 8
procname ENDP

<identifier>CODE ENDS
END