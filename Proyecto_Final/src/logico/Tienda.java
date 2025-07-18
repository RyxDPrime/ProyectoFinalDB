 package logico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Tienda {
	private ArrayList<Cliente> misClientes;
	private ArrayList<Componente> misComponentes;
	private ArrayList<Factura> misFacturas;
	private ArrayList<Computadora> misComputadoras;
	private ArrayList<Usuarios> misUsuarios;
	private static Tienda miTienda = null; 
	public static int codCliente = 1;
	public static int codComponente = 1;
	public static int codFactura = 1;
	public static int codComputadora = 1;
	public Boolean permisoAdministrado = false;
	
	public Tienda() {
		super();
		this.setMisUsuarios(new ArrayList<Usuarios>());
		this.misClientes = new ArrayList<Cliente>();
		this.misComponentes = new ArrayList<Componente>();
		this.misFacturas = new ArrayList<Factura>();
		this.misComputadoras = new ArrayList<Computadora>();
	}
	
	
	public static Tienda getInstance() {
		if(miTienda == null) {
			miTienda = new Tienda();
		} return miTienda;
	}

	public ArrayList<Cliente> getMisClientes() {
		return misClientes;
	}

	public ArrayList<Componente> getMisComponentes() {
		return misComponentes;
	}
	
	public ArrayList<Factura> getMisFacturas() {
		return misFacturas;
	}

	public ArrayList<Computadora> getMisComputadoras() {
		return misComputadoras;
	}

	public static int getCodCliente() {
		return codCliente;
	}

	public static int getCodComponente() {
		return codComponente;
	}

	public static int getCodFactura() {
		return codFactura;
	}

	public static int getCodComputadora() {
		return codComputadora;
	}

	public void setMisFacturas(ArrayList<Factura> misFacturas) {
		this.misFacturas = misFacturas;
	}

	public void setMisClientes(ArrayList<Cliente> misClientes) {
		this.misClientes = misClientes;
	}

	public void setMisComponentes(ArrayList<Componente> misComponentes) {
		this.misComponentes = misComponentes;
	}
	
	public void setMisComputadoras(ArrayList<Computadora> misComputadoras) {
		this.misComputadoras = misComputadoras;
	}

	public ArrayList<Usuarios> getMisUsuarios() {
		return misUsuarios;
	}


	public void setMisUsuarios(ArrayList<Usuarios> misUsuarios) {
		this.misUsuarios = misUsuarios;
	}


	public void insertarCliente(Cliente cliente) {
		misClientes.add(cliente);
		codCliente++;
	}
	
	public void insertarFactura(Factura factura) {
		misFacturas.add(factura);
		codFactura++;
	}
	
	public void insertarComponente(Componente componente) {
		misComponentes.add(componente);
		codComponente++;
	}
	
	public void insertarComputadora(Computadora computadora) {
		misComputadoras.add(computadora);
		codComputadora++;
	}
	
	public void insertarUsuario(Usuarios usuario) {
		misUsuarios.add(usuario);
	}
	
	public void updateCliente(Cliente cliente, int ind) {
		misClientes.set(ind, cliente);
	}
	
	public void updateComponente(Componente componente, int ind) {
		misComponentes.set(ind, componente);
	}
	
	public void updateFactura(Factura factura, int ind) {
		misFacturas.set(ind, factura);
	}
	
	public void updateComputadora(Computadora computadora, int ind) {
		misComputadoras.set(ind, computadora);
	}
	
	public void deleteCliente(String idcliente) {
		Cliente cliente = searchClienteById(idcliente);
		misClientes.remove(cliente);
	}
	
	public void deleteComponente(String idcomponente) {
		Componente componente = searchComponenteById(idcomponente);
		misComponentes.remove(componente);
	}
	
	public void deleteFactura(String idfactura) {
		Factura factura = searchFacturaById(idfactura);
		misFacturas.remove(factura);
	}
	
	public void deleteComputadora(String idcomputadora) {
		Computadora computadora = searchComputadoraById(idcomputadora);
		misComputadoras.remove(computadora);
	}
	
	public Componente searchComponenteMasVendido() {
		Componente componenteMasVendido = new Componente(null, null, null, 0, 0, 0); // Poner que el componente al menos tenga 0 unidadades vendidas
		
		for ( int i = 0; i < misComponentes.size(); i++ ) {
			if ( misComponentes.get(i).getCantVendidos() > componenteMasVendido.getCantVendidos() ) {
				componenteMasVendido = misComponentes.get(i);
			}
		}
		return componenteMasVendido;
	}
	
	public Componente searchComponenteById(String id) {
		Componente componente = null;
		boolean encontrado = false;
		int i = 0;
		
		while ( !encontrado && i < misComponentes.size() ) {
			if ( misComponentes.get(i).getId().equals(id) ) {
				encontrado = true;
				componente = misComponentes.get(i);
			}
			i++;
		}
		return componente;
	}
	
	public ArrayList<Componente> getListComponentesMasVendidos() {
	    ArrayList<Componente> componentesMasVendidos = new ArrayList<>(misComponentes);
	    
	    // Filtrar componentes con cantidad disponible > 0
	    componentesMasVendidos.removeIf(comp -> comp.getCantDisponible() <= 0);
	    
	    // Ordenar la lista de componentes por cantidad vendida (de mayor a menor)
	    Collections.sort(componentesMasVendidos, new Comparator<Componente>() {
	        @Override
	        public int compare(Componente comp1, Componente comp2) {
	            return Integer.compare(comp2.getCantVendidos(), comp1.getCantVendidos());
	        }
	    });
	    
	    return componentesMasVendidos;
	}
	
	public ArrayList<Computadora> getListComputadorasMasVendidas() {
	    ArrayList<Computadora> computadorasMasVendidas = new ArrayList<>(misComputadoras);
	    
	    // Filtrar computadoras con cantidad disponible > 0
	    computadorasMasVendidas.removeIf(comp -> comp.getCantDisponible() <= 0);
	    
	    // Ordenar la lista de computadoras por cantidad vendida (de mayor a menor)
	    Collections.sort(computadorasMasVendidas, new Comparator<Computadora>() {
	        @Override
	        public int compare(Computadora comp1, Computadora comp2) {
	            return Integer.compare(comp2.getCantVendido(), comp1.getCantVendido());
	        }
	    });
	    
	    return computadorasMasVendidas;
	}

	
	public Cliente searchClienteById(String id) {
		Cliente cliente = null;
		boolean encontrado = false;
		int i = 0;
		
		while ( !encontrado && i < misClientes.size() ) {
			if ( misClientes.get(i).getId().equals(id) ) {
				encontrado = true;
				cliente = misClientes.get(i);
			}
			i++;
		}
		return cliente;
	}
	
	public Factura searchFacturaById(String id) {
		Factura factura = null;
		boolean encontrado = false;
		int i = 0;
		
		while ( !encontrado && i < misFacturas.size() ) {
			if ( misFacturas.get(i).getId().equals(id) ) {
				encontrado = true;
				factura = misFacturas.get(i);
			}
			i++;
		}
		return factura;
	}
	
	public Computadora searchComputadoraById(String id) {
		Computadora computadora = null;
		boolean encontrado = false;
		int ind = 0;
		
		while(!encontrado && ind < misComputadoras.size()) {
			if(misComputadoras.get(ind).getId().equalsIgnoreCase(id)) {
				encontrado = true;
				computadora = misComputadoras.get(ind);
			}
			ind++;
		}
		return computadora;
	}
	
	public float calcSubTotalComponentes(ArrayList<Componente> componentes) {
		float subTotal = 0;
		
		for(Componente comp : componentes) {
			subTotal += comp.getPrecio() * comp.getCantSeleccionado();
		}
		return subTotal;
	}
	public float calcPrecioTotalComponentes(ArrayList<Componente> componentes) {
		float precio = 0;
		int porcentaje = 0;
		float precioTotal = 0;
		
		for(Componente comp : componentes) {
			precio = comp.getPrecio() * comp.getCantSeleccionado();
			porcentaje = comp.getDescuento();
			precioTotal += (float) (precio - ((porcentaje * precio) / 100));
		}
		return precioTotal;
	}
	
	public float calcSubTotalComputadora(ArrayList<Componente> componentes) {
		float subTotal = 0;
		
		for(Componente componente : componentes) {
			subTotal += componente.getPrecio();
		}
		return subTotal;
	}
	
	public float calcPrecioTotalComputadora(ArrayList<Componente> componentes) {
		float precio = 0;
		int porcentaje = 0;
		float precioTotal = 0;
		
		for(Componente componente : componentes) {
			precio = componente.getPrecio();
			porcentaje = componente.getDescuento();
			precioTotal += (float) (precio - ((porcentaje * precio) / 100));
		}
		return precioTotal;
	}
	
	public float calcDescuentoTotalComponentes(ArrayList<Componente> componentes) {
		float descuentoTotal = 0;
	    
	    for (Componente comp : componentes) {
	        float precio = comp.getPrecio();
	        float descuento = comp.getDescuento(); 
	        
	        float descuentoAplicado = precio * (descuento / 100);
	        
	        descuentoTotal += descuentoAplicado;
	    }
	    
	    return descuentoTotal;
	}
	
	public float calcDescuentoTotalComputadora(ArrayList<Componente> componentes) {
		float descuentoTotal = 0;
		
		try {
		    for (Componente comp : componentes) {
		        float precio = comp.getPrecio();
		        float descuento = comp.getDescuento(); 
		        
		        float descuentoAplicado = precio * (descuento / 100);
		        
		        descuentoTotal += descuentoAplicado;
		    }
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("La computadora debe tener exactamente 5 componentes.");
		}
		return descuentoTotal;
	}
	
	public ArrayList<Componente> filterByMarca(String marca){
		ArrayList<Componente> componentesByMarca = new ArrayList<>();
		
		for ( Componente comp : misComponentes ) {
			if ( comp.getMarca().equals(marca) ) {
				componentesByMarca.add(comp);
			}
		}
		return componentesByMarca;
	}
	
	public ArrayList<Componente> filterByPrecio(float precioMinimo, float precioMaximo){
		ArrayList<Componente> componentesByPrecio = new ArrayList<>();
		
		for ( Componente comp : misComponentes ) {
			if ( comp.getPrecio() > precioMinimo && comp.getPrecio() < precioMaximo ) {
				componentesByPrecio.add(comp);
			}
		}
		return componentesByPrecio;
	}
	
	public Factura makeFactura(ArrayList<Componente> componentes, int[] cantidadesGastadas, Computadora pc, int cantidadPc, Cliente cliente) {
	    float precioTotal = 0;
	    float descuento = 0;
	    float subtotal = 0;
	    boolean finalizado = false;
	    Factura factura = null;
	    String id = "F-" + codFactura;
	    Date fechaActual = new Date();
 

	    if (componentes != null && pc == null) {
	    	subtotal = calcSubTotalComponentes(componentes);
	    	precioTotal = calcPrecioTotalComponentes(componentes);
	    	descuento = calcDescuentoTotalComponentes(componentes);
	        
	        ArrayList<Componente> carrito = componentes;
	        
	        int[] cantArticulos = calcularCantidadArticulos(carrito);
	        factura = new FacturaComponente(cliente.getId(), id, subtotal, descuento, precioTotal, fechaActual, cantidadesGastadas, carrito);
	        finalizado = true;
	        if (finalizado && cantidadesGastadas != null) {
	        	actualizarCantidadesDisponibles(cantidadesGastadas, componentes);
	        }
	    } else if (pc != null && componentes == null) {
	    	subtotal = calcSubTotalComputadora(pc.getComponentes()) * cantidadPc;
	        precioTotal = calcPrecioTotalComputadora(pc.getComponentes()) * cantidadPc;
	        descuento = calcDescuentoTotalComputadora(pc.getComponentes());
	        
	        factura = new FacturaComputadora(cliente.getId(), id, subtotal, descuento, precioTotal, fechaActual, 1, pc.getId(), pc.getComponentes(), cantidadPc);
	        finalizado = true;
	    }

	    if (finalizado) {
	        insertarFactura(factura);
	    }

	    return factura;
	}
	
	public void actualizarCantidadesDisponibles(int[] cantidadesGastadas, ArrayList<Componente> componentes) {
		System.out.println("b"+cantidadesGastadas.length);
		System.out.println("b"+componentes.size());
	    
	    for (int i = 0; i < componentes.size(); i++) {
	        Componente componente = componentes.get(i);
	        int cantidadGastada = cantidadesGastadas[i];
	        
	        componente.setCantDisponible(componente.getCantDisponible() - cantidadGastada);
	    }
	}

	private int[] calcularCantidadArticulos(ArrayList<Componente> carrito) {
	    int[] cantidadArticulos = new int[carrito.size()];
	    
	    for (int i = 0; i < carrito.size(); i++) {
	        cantidadArticulos[i] = carrito.get(i).getCantSeleccionado();
	    }
	    
	    return cantidadArticulos;
	}
	
	public boolean makeOferta(Componente componente) {
		boolean oferta = false;
		int cantVendido = componente.getCantVendidos();
		if(cantVendido > 15) {
			componente.setDescuento(componente.getDescuento() + 15);
			oferta = true;
		}
		else if(cantVendido <= 15) {
			componente.setDescuento(componente.getDescuento()+ 10);
			oferta = true;
		}
		else if(cantVendido <= 10) {
			componente.setDescuento(componente.getDescuento() + 5);
			oferta = true;
		}
		return oferta;
	}
	
	public boolean makeComputadora(String id, ArrayList<Componente> componentes, int cantDisponibles, String tipo, int cantVendido) {
		boolean creado = false;
		float precioPc = calcPrecioTotalComputadora(componentes);
		Computadora pc = new Computadora(id, componentes, precioPc, cantDisponibles, tipo, cantVendido);
		
		if ( isCompatible(pc) ) {
			creado = true;
			insertarComputadora(pc);
		}
		return creado;
	}
	
	public boolean isCompatible(Computadora pc) {
		boolean compatible = false;
		
		TarjetaMadre tarjetaMadre = ((TarjetaMadre) pc.getComponentes().get(0));
		Ram ram = ((Ram) pc.getComponentes().get(1));
		MicroProcesador microProcesador = ((MicroProcesador) pc.getComponentes().get(2));
		DiscoDuro discoDuro = ((DiscoDuro) pc.getComponentes().get(3));
		
		if( tarjetaMadre.getTipoRam().equals(ram.getTipoMemoria()) && 
			tarjetaMadre.getConectionSocket().equals(microProcesador.getTipoConexion()) ) {
			
			for ( String tipo : tarjetaMadre.getTipoDiscoDuro() ) {
				for ( String tipo2 : discoDuro.getTipoConexiones() ) {
					if ( tipo.equals(tipo2) ) {
						compatible = true;
						break;
					}
				}
				
			}
			
		}
		return compatible;
	}
	
	public boolean esCompatibleConTarjetaMadre(Componente componente, TarjetaMadre tarjetaMadre) {
	    if (componente instanceof GPU) {
	        GPU gpu = (GPU) componente;
	        return gpu.getTipoConexion().equalsIgnoreCase(tarjetaMadre.getConectionGPU());
	    } else if (componente instanceof MicroProcesador) {
	        MicroProcesador micro = (MicroProcesador) componente;
	        return micro.getTipoConexion().equalsIgnoreCase(tarjetaMadre.getConectionSocket());
	    } else if (componente instanceof Ram) {
	        Ram ram = (Ram) componente;
	        return ram.getTipoMemoria().equalsIgnoreCase(tarjetaMadre.getTipoRam());
	    } else if (componente instanceof DiscoDuro) {
	        DiscoDuro disco = (DiscoDuro) componente;
	        for (String conexion : disco.getTipoConexiones()) {
	            if (tarjetaMadre.getTipoDiscoDuro().contains(conexion)) {
	                return true;
	            }
	        }
	        return false;
	    }
	    return false; 
	}
	
	public boolean returnStockStatus(Componente componente) {
	    int minimo = 10; 
	    return componente.getCantDisponible() < minimo;
	}
	
	public void cargarArchivo() {
	    try {
	        File archivo = new File("objetos.dat");
	        if (!archivo.exists()) {
	            archivo.createNewFile();
	            System.out.println("Archivo creado: " + "objetos.dat");
	        }else {
	            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("objetos.dat"))) {
	                ArrayList<Object> objetos = (ArrayList<Object>) ois.readObject();
	                for (Object obj : objetos) {
	                    if (obj instanceof Cliente) {
	                        misClientes.add((Cliente) obj);
	                    } else if (obj instanceof Componente) {
	                    	if(obj instanceof Ram) {
	                    		misComponentes.add((Ram) obj);
	                    	}else if (obj instanceof DiscoDuro) {
	                    		misComponentes.add((DiscoDuro) obj);
							}else if (obj instanceof GPU) {
								misComponentes.add((GPU) obj);
							}else if (obj instanceof TarjetaMadre) {
								misComponentes.add((TarjetaMadre) obj);
							}else if (obj instanceof MicroProcesador) {
								misComponentes.add((MicroProcesador) obj);
							}
	                    } else if (obj instanceof Factura) {
	                        Tienda.getInstance().insertarFactura((Factura) obj);
	                    } else if (obj instanceof Computadora) {
	                        Tienda.getInstance().insertarComputadora((Computadora) obj);
	                    }else if (obj instanceof Usuarios) {
	                    	misUsuarios.add((Usuarios) obj);
	                    }
	                }
	            }
	        }
	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	public void escribirArchivo() {
        ArrayList<Object> objetos = new ArrayList<>();
        objetos.addAll(misClientes);
        objetos.addAll(misComponentes);
        objetos.addAll(misComputadoras);
        objetos.addAll(misFacturas);
        objetos.addAll(misUsuarios);


        try (FileOutputStream fos = new FileOutputStream("objetos.dat", false);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

               oos.writeObject(objetos);

           } catch (IOException e) {
               e.printStackTrace();
           }
    }


	public Boolean getPermisoAdministrado() {
		return permisoAdministrado;
	}


	public void setPermisoAdministrado(Boolean permisoAdministrado) {
		this.permisoAdministrado = permisoAdministrado;
	}

}
