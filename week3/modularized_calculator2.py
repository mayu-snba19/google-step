
def readNumber(line, index):
  number = 0
  while index < len(line) and line[index].isdigit():
    number = number * 10 + int(line[index])
    index += 1
  if index < len(line) and line[index] == '.':
    index += 1
    keta = 0.1
    while index < len(line) and line[index].isdigit():
      number += int(line[index]) * keta
      keta /= 10
      index += 1
  token = {'type': 'NUMBER', 'number': number}
  return token, index


def readPlus(line, index):
  token = {'type': 'PLUS'}
  return token, index + 1

def readMinus(line, index):
  token = {'type': 'MINUS'}
  return token, index + 1

def readMulti(line, index):
  token = {'type': 'MULTI'}
  return token, index + 1

def readDivi(line, index):
  token = {'type': 'DIVI'}
  return token, index + 1

def tokenize(line):
  tokens = []
  index = 0
  while index < len(line):
    if line[index].isdigit():
      (token, index) = readNumber(line, index)
    elif line[index] == '+':
      (token, index) = readPlus(line, index)
    elif line[index] == '-':
      (token, index) = readMinus(line, index)
    elif line[index] == '*':
      (token, index) = readMulti(line, index)
    elif line[index] == '/':
      (token, index) = readDivi(line, index)
    else:
      print('Invalid character found: ' + line[index])
      exit(1)
    tokens.append(token)
  return tokens


def evaluate(tokens):
  answer = 0
  tokens.insert(0, {'type': 'PLUS'}) # Insert a dummy '+' token
  index = 1
  while index < len(tokens):
    if tokens[index]['type'] == 'MULTI':
      tokens[index+1]['number'] *= tokens[index-1]['number']
      tokens[index-1]['number'] = 0
      if(index-2 >= 0 and tokens[index-2]['type'] == 'MINUS'):
        tokens[index]['type'] = 'MINUS'
      else:
        tokens[index]['type'] = 'PLUS'
        index += 1
    if tokens[index]['type'] == 'DIVI':
      tokens[index+1]['number'] = tokens[index -1]['number'] / tokens[index+1]['number']
      tokens[index-1]['number'] = 0
      if(index-2 >= 0 and tokens[index-2]['type'] == 'MINUS'):
        tokens[index]['type'] = 'MINUS'
      else:
        tokens[index]['type'] = 'PLUS'
      index += 1
    index+=1

  index = 0
  while index < len(tokens):
    if tokens[index]['type'] == 'NUMBER':
      if tokens[index - 1]['type'] == 'PLUS':
        answer += tokens[index]['number']
      elif tokens[index - 1]['type'] == 'MINUS':
        answer -= tokens[index]['number']
      else:
        print('Invalid syntax')
        exit(1)
    index += 1
  return answer

def calculate(line):
  # 括弧内を先に計算する。
  while True:
    # ALEX_COMMENT:  the call below to findBracket parses the same strings
    #                multiple times. Its an inefficiency.  Can you think  of a way
    #                to avoid repeated parsing?
    (left, right) = findBracket(line)
    if left== -1:
      break
    tokens = tokenize(line[left+1:right])
    nowans = evaluate(tokens)
    line = line[:left]+str(nowans)+line[right+1:] #括弧内を計算結果に書き換える。
  tokens = tokenize(line)
  ans = evaluate(tokens)
  return ans

# ALEX_COMMENT:  very good, conside comment

# 括弧のペアを見つけたら括弧の範囲を返す。見つけられなかったら-1,-1
def findBracket(line):
  for right in range(0,len(line)):
    if(line[right]==')'):
      for left in range(right-1,-1,-1):
        if(line[left]=='('):
          return left, right
  return -1,-1


def test(line):
  actualAnswer = calculate(line)
  expectedAnswer = eval(line)
  if abs(actualAnswer - expectedAnswer) < 1e-8:
    print("PASS! (%s = %f)" % (line, expectedAnswer))
  else:
    print("FAIL! (%s should be %f but was %f)" % (line, expectedAnswer, actualAnswer))


# Add more tests to this function :)
def runTest():
  print("==== Test started! ====")
  test("1+2")
  test("1.5+2.5")
  test("1.5+2.5-3.0")
  test("2*3")
  test("2*3+2")
  test("2+2*3")
  test("2*3*2")
  test("4/2+2")
  test("2+4/2")
  test("2*3*2")
  test("8/2/2")
  test("2*3+4*5")
  test("8/2-4/2")
  test("(1.5+0.5)*3")
  test("2*(1.0-0.5)")
  test("(3.0-2.0)")
  test("(6.0+2.0)/(4.0)")
  test("(3.0+4*(2+1))/5")
  test("((3+4)*2+1)/5")
  test("5*((3+4)*2+1)")
  print("==== Test finished! ====\n")]
  
  # ALEX_COMMENT:  no testing for unmatched parenthesis or mal-formed expressions.
  #                I have a feeling that findBracket is not able to detect those conditions

runTest()

while True:
  print('> ', end="")
  line = input()
  answer = calculate(line)
  print("answer = %f\n" % answer)
