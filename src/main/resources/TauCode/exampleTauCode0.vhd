library ieee;
library altera_mf;
use ieee.std_logic_1164.all;
use altera_mf.altera_mf_components.all;
use ieee.std_logic_unsigned.all;

entity project_tau_k is
port(f0 : in std_logic;
tau : in std_logic;
Nx : in std_logic_vector (4 downto 1);
Ny : out std_logic_vector (4 downto 1)
);
end project_tau_k;

architecture behav of project_tau_k is
signal cnt : std_logic_vector(4 downto 1);
signal cnt2 : std_logic_vector(4 downto 1);

begin
process(f0)
begin
if (f0='1' and f0'event)
then 
if (tau = '1') 
then cnt <= cnt - 1;
else if (tau = '0') then cnt <= "0000";
end if;
end if;
end if;
end process;

process(f0)
begin
if (cnt = 0)
then cnt2 <= Nx + 1;
else
if (tau = '1')  then
if (f0='1' and f0'event) 
then cnt2 <= cnt2 + 1;
end if;
end if;
end if;
end process;

process(Nx)
begin
if (tau = '0' and cnt /= 0)
then Ny <= cnt2;
end if;
end process;

end behav;