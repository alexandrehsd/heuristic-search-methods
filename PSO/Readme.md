In this reposity you can find an implementation of the Particles swarm optimization (PSO) algorithm applied in order to find the global minima of any 2-dimensional function. For the sake of clarity and a proper understanding, the function used as an example is the <a href="https://en.wikipedia.org/wiki/Rosenbrock_function">Rosenbrock's Function</a>. It is given by the equation

![Rosenbrock equation](http://latex.codecogs.com/gif.latex?f%28x%2Cy%29%20%3D%20%28a-x%29%5E2%20&plus;%20b%28y-x%5E2%29%5E2)

For convenience, I set ![a](http://latex.codecogs.com/gif.latex?a%3D1) and ![](http://latex.codecogs.com/gif.latex?b%3D100). In these conditions, the global minima is known and it is given by
 
 ![Global minima](http://latex.codecogs.com/gif.latex?f%281%2C1%29%20%3D%200)

The Rosenbrock's function with these parameters has the following shape:

![Rosenbrock's function](Rosenbrock_function.png)

Without lack of generalization, one can apply the algorithm to any 2-dimensional function by just modifying some parameters at the source files. Note that the equations for the velocity and the position of the particles at each i-dimension at an instant ![t](http://latex.codecogs.com/gif.latex?%5Cinline%20%5Cdpi%7B150%7D%20%5Csmall%20t%20%3D%20n&plus;1) is given by

![velocity](http://latex.codecogs.com/gif.latex?%5Cinline%20%5Cdpi%7B150%7D%20%5Csmall%20v_i%28n&plus;1%29%20%3D%20wv_i%28n%29%20&plus;%20%5Crho_%7B1%7DC_1%28p_%7Bbest%7D_%7Bi%7D%28n%29%20-%20pos_%7Bi%7D%28n%29%29%20&plus;%20%5Crho_%7B2%7DC_2%28g_%7Bbest%7D%28n%29%20-%20pos_%7Bi%7D%28n%29%29)

![position](http://latex.codecogs.com/gif.latex?%5Cinline%20%5Cdpi%7B150%7D%20%5Csmall%20pos_i%28n&plus;1%29%20%3D%20pos_i%28n%29%20&plus;%20v_i%28n&plus;1%29)

where, ![rho1](http://latex.codecogs.com/gif.latex?%5Cinline%20%5Cdpi%7B150%7D%20%5Csmall%20%5Crho_1) and ![rho2](http://latex.codecogs.com/gif.latex?%5Cinline%20%5Cdpi%7B150%7D%20%5Csmall%20%5Crho_2) are random variables between [0,1]. The constants ![C1](http://latex.codecogs.com/gif.latex?%5Cinline%20%5Cdpi%7B150%7D%20%5Csmall%20C_1) (called factor of cognitive learning) and ![C1](http://latex.codecogs.com/gif.latex?%5Cinline%20%5Cdpi%7B150%7D%20%5Csmall%20C_2) (called factor of social learning) represent learning factors, i. e., how much the movement of each particle will be influenced by the performance of the "best" particle and the own particle's best performance. Last but not least, the ![w](http://latex.codecogs.com/gif.latex?%5Cinline%20%5Cdpi%7B150%7D%20%5Csmall%20w) parameter is called inertial factor, and it represents the tendency that each particle has to keep going on the same direction (Inertia!).

The appropriate values for these parameters varies from case to case, therefore, a series of tests can be needed in order to find the proper values for these factors. For the Rosenbrock's function, I set

![parameters](http://latex.codecogs.com/gif.latex?%5Cinline%20%5Cdpi%7B150%7D%20%5Csmall%20%5C%5C%20C_1%20%3D%200.5%20%5C%5C%20C_2%20%3D%200.5%20%5C%5C%20w%20%3D%200.8)

In addition, it is also important to initialiaze the particles in an interval in which you believe that contains the global minima of the function you are analyzing. For the Rosenbrock's function, <i>x</i> and <i>y</i> values of the initial positions starts at the range [0,20]. One can modify all these parameters at the PSO.java file.

Lastly, you can set the function you wish to apply the PSO algorithm in the Evaluation method, which is implemented at the Evaluation.java file. To do this, write your function as the return argument of the Evaluation method, considering that pos[0] and pos[1] represents the <i>x</i> and <i>y</i> values for the coordinates of the particles in a 2-dimensional <i>xy</i> plane.

