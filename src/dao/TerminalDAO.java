/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.Terminal;
import java.util.List;

/**
 *
 * @author jcapax
 */
public interface TerminalDAO {
    public byte getIdTerminal(String hostName);
    public String getNameHost();
    public void insertarTerminal(Terminal terminal);
    public List<Terminal> getListTerminal();
    public void eliminarTerminal(int idTerminal);
    public boolean existsTerminal(String hostname);
    public int getIdterminalNueva();
    
}
