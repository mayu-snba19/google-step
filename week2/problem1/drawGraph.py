from matplotlib import pyplot as plt
import numpy as np

f = open('output.txt','r',encoding='UTF-8')

datalist = f.readlines()
x=[]
y=[]
for data in datalist:
  x.append(data.split(' ')[0])
  y.append(float(data.split(' ')[1].replace('\n','')))
f.close()

fig, ax = plt.subplots()
ax.plot(x, y)
ax.set_xlabel("size")
ax.set_ylabel("second")

plt.plot(x, y)
plt.xticks(np.arange(-1, 700, 50))
plt.yticks(np.arange(0.0, 2.0, 0.1))

plt.show()
