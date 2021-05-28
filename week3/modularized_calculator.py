
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

  # 掛け算・割り算を先に処理
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
      tokens[index+1]['number'] = tokens[index-1]['number'] / tokens[index+1]['number']
      tokens[index-1]['number'] = 0
      if(index-2>=0 and tokens[index-2]['type']=='MINUS'):
        tokens[index]['type'] = 'MINUS'
      else:
        tokens[index]['type'] = 'PLUS'
      index+=1
    index+=1

  # 足し算・引き算を処理
  index = 1
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


def test(line):
  tokens = tokenize(line)
  actualAnswer = evaluate(tokens)
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
  print("==== Test finished! ====\n")

runTest()

while True:
  print('> ', end="")
  line = input()
  tokens = tokenize(line)
  answer = evaluate(tokens)
  print("answer = %f\n" % answer)
