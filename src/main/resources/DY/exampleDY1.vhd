library ieee;
library altera_mf;
use ieee.std_logic_1164.all;
use altera_mf.altera_mf_components.all;
use ieee.std_logic_unsigned.all;

entity vhdl_du is
  port(f0 :in std_logic; -- reference frequency
	   w  : in std_logic_vector (4 downto 1); -- весовой коэффициент
	   f_2_o, f_4_o, f_8_o, f_16_o : out std_logic; -- разделенная частота
	   chim : out std_logic  -- CHIM result
	  );
end vhdl_du;


architecture behav of vhdl_du is
signal cnt : std_logic_vector(4 downto 1);
signal cnt_t : std_logic_vector(4 downto 1);
signal f_2, f_4, f_8, f_16 : std_logic;
signal shim_in : std_logic;

begin

process(f0)
begin
 if (f0='1' and f0'event)
  then cnt <= cnt + 1;
 end if;
end process;

process(f0)
begin
 if (f0='1' and f0'event)
  then cnt_t <= cnt;
 end if;
end process;

f_2 <= w(4) and cnt(1) and (not cnt_t(1));
f_4 <= w(3) and cnt(2) and (not cnt_t(2));
f_8 <= w(2) and cnt(3) and (not cnt_t(3));
f_16 <= w(1) and cnt(4) and (not cnt_t(4));

shim_in <= f_2 or f_4 or f_8 or f_16;
chim <= f0 and shim_in;

f_2_o <= f_2;
f_4_o <= f_4;
f_8_o <= f_8;
f_16_o <= f_16;
   
end behav;
