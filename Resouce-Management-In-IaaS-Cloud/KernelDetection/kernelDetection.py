import scipy
import numpy as np
from scipy import ndimage
import sklearn
from sklearn import svm
import cPickle as pickle
import math

def main():
    data1 =open("datasets/set/user1_cpu.csv","r+").read().split("\n")
    #print data1
    data2 = open("datasets/set/user1_mem.csv").read().split("\n")
    #print data2
    [x2,y2,z2,datList2] = calcMean(data2,1.0)
    [x1,y1,z1,datList1] = calcMean(data1,100.0)
    [result21,result22] = getSampleSize(5,datList2)
    [result11,result12] = getSampleSize(5,datList1) 
    print "resultttttttttttttttt"
    print result11[0]
    print result11[0]
    #[yval1, yval2] = generateYVals(result11[0],result12[0],z1)
    
    [w1,w2]=trainSVM(result11[0],result12[0],result21[0],result22[0],0.04)
    print "trainingggggggggggggggggg"
    
    normal = generateVectorPlane(w1,w2)
    result = computeArcLength(w1,w2)

def calcMean(datList,maxVal):
    mean =0.0
    normalList=[]
    #calculate mean for gaussian
    for elem in datList:
        try:
            normalList.append((float(elem))/maxVal)
            mean = mean + (float(elem)/maxVal)/len(datList)
        except:
            print "There was an error with item "+elem
    print "Mean: "
    #print mean

    #calculate variance
    variance =0
    for elem in normalList:
        variance = variance + (elem - mean)*(elem-mean)/len(datList)
        
    print "Variance "
    #print variance
    stdDev = math.sqrt(variance)
    return [mean,variance,stdDev,normalList]
    print "ooooooooooooooooooooooooooooooooooooooooooooooooo"
    print [mean,variance,stdDev,normalList]

def getSampleSize(chunkSize,datList):
    m1List =[]
    m2List =[]
    chunkSentinel = False
    count =0
    while chunkSentinel == False:
        temp1=[]
        temp2=[]
        for i in xrange(2*chunkSize):
            if i < chunkSize:
                temp1.append(2*datList[i+count]-1)
            else:
                temp2.append(2*datList[i+count]-1)
        count = count + len(temp1)
        m1List.append(temp1)
        m2List.append(temp2)
        if len(datList) - count < 2*chunkSize:
            print "getSampleSize: m1 and m2"
            chunkSentinel = True 
    return [m1List, m2List]

def generateYVals(mVals1,mVals2,sigma1):
    print " Input is "
    print mVals1
    print mVals2
    yVals1 =scipy.ndimage.filters.gaussian_filter1d(mVals1,sigma=sigma1)
    yVals2 = scipy.ndimage.filters.gaussian_filter1d(mVals2,sigma=sigma1)
    print "First set of values"
    print yVals1
    print "Second set of values"
    print yVals2
    return [yVals1, yVals2]

def trainSVM(m1,y1,nu):
    classifier1 = sklearn.svm.OneClassSVM(kernel='linear', nu =nu,gamma ='auto')
    classifier2 = sklearn.svm.OneClassSVM(kernel='linear', nu =nu,gamma='auto')
    
    t1=[]
    t2=[]
    #print m1
    #print m2
    #print "In function: trainSVM: begin loop"
    for x in xrange(len(m1)):
	print" Iteration "+str(x)
        t1.append([m1[x],y1[x]])
	t2.append([m2[x],y2[x]])
    tx = np.array(t1) 
    ty = np.array(t2)

   # t1 = np.r_[tx+1.0,tx-1.0]
    #t2 = np.r_[ty+1.0,ty-1.0] 
    print "tttttttttttttttttttttttttttttttttttttttttt"
    #print t1
    #print t2

    clfit1 = classifier1.fit(tx)   
    clfit2 = classifier2.fit(ty)
  
    #print clfit1
    #print clfit2
    
    w1 = clfit1.coef_
    w2 = clfit2.coef_    
    print "planeeeeeeeeee"
    print w1
    #print w2
    #print clfit1.support_vectors_
    #print clfit2.support_vectors_
    print "wwwwwwwwwwwwwwwwwwwwwwwwwwwww"
    print [w1,w2]
    
    return [w1, w2]

def generateVectorPlane(w1,w2):
    print " In function generateVectorPlane: "
    planeVec =  np.cross(w1,w2)
    print "In function generateVectorPlane: the vector is "+str(planeVec)
    normPlaneVec = planeVec/ math.sqrt(sum([n**2 for n in planeVec]))
    print "sssssssssssssssssssssssssssssss"
    print normPlaneVec 
    return normPlaneVec
                		
def computeArcLength(x1,x2): 
    c1c2Prod = np.dot(x1,np.transpose(x2))
    print c1c2Prod
    c1c2 = c1c2Prod/(np.linalg.norm(x1)*np.linalg.norm(x2))
    print c1c2
    result = np.arccos(c1c2)
    print result
    return None

def computeDecisionIndex(x1,x2,gKernel2D):
    result=None

    return result
if __name__ == "__main__":
    main()
