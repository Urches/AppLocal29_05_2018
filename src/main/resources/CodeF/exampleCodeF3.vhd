library ieee;
library altera_mf;
use ieee.std_logic_1164.all;
use altera_mf.altera_mf_components.all;
use ieee.std_logic_unsigned.all;

entity Check is
port(f0 : in std_logic;
x : in std_logic_vector (4 downto 1); 
theta : in std_logic_vector (4 downto 1); 
fy : out std_logic);
end Check;

architecture behav of Check is
signal cnt : std_logic_vector(4 downto 1);
signal fy_in : std_logic;

begin
process(f0)
begin 
if (fy_in = '1')
then cnt <= theta;
else if (f0='1' and f0'event)
then cnt <= cnt - 1; 
end if;
end if;
end process;

process(cnt,x)
begin 
if (cnt = x)
then fy_in <= '1';
else fy_in <= '0';
end if;
end process;
fy <= fy_in and f0;
end behav;