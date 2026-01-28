'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import { usePathname, useParams } from 'next/navigation';
import { ThemeToggle } from './ThemeToggle';
import { LanguageSwitcher } from './LanguageSwitcher';
import { motion, AnimatePresence } from 'framer-motion';
import { useLanguage } from './LanguageProvider';
import { FileText, User, Folder, Mail, Menu, X } from 'lucide-react';

export function Header() {
  const pathname = usePathname();
  const params = useParams();
  const { t } = useLanguage();
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);
  }, []);

  const navItems = [
    { name: t('navigation.home'), path: '/', icon: FileText },
    { name: t('navigation.about'), path: '/about', icon: User },
    { name: t('navigation.projects'), path: '/projects', icon: Folder },
    { name: t('navigation.contact'), path: '/contact', icon: Mail },
  ];

  const getActiveState = (itemPath: string) => {
    if (!mounted) return false;

    if (itemPath === '/') {
      return pathname === '/' || pathname === '';
    }

    return pathname === itemPath || pathname.startsWith(`${itemPath}/`);
  };

  if (!mounted) {
    return (
      <header className="sticky top-0 z-50 bg-card/80 backdrop-blur-md border-b border-border">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <div className="text-2xl font-bold font-mono">wkrn</div>
            <div className="flex items-center space-x-4">
              <div className="w-20 h-9 bg-muted rounded-lg animate-pulse" />
              <div className="w-9 h-9 bg-muted rounded-lg animate-pulse" />
            </div>
          </div>
        </div>
      </header>
    );
  }

  return (
    <motion.header
      initial={{ y: -100 }}
      animate={{ y: 0 }}
      className="sticky top-0 z-50 bg-card/80 backdrop-blur-md border-b border-border"
    >
      <div className="container mx-auto px-4 py-4">
        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-8">
            <Link href="/" className="text-2xl font-bold font-mono">
              wkrn
            </Link>

            <nav className="hidden md:flex space-x-2">
              {navItems.map((item, index) => {
                const isActive = getActiveState(item.path);

                return (
                  <motion.div
                    key={item.path}
                    initial={{ opacity: 0, y: -20 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ delay: index * 0.1 }}
                  >
                    <Link
                      href={item.path}
                      className={`px-4 py-2 rounded-lg font-medium transition-all duration-300 flex items-center gap-2
                        ${
                          isActive
                            ? 'bg-accent text-accent-foreground shadow-md'
                            : 'text-muted-foreground hover:text-accent hover:bg-accent/10'
                        }
                      `}
                    >
                      <item.icon className="w-4 h-4" />
                      {item.name}
                    </Link>
                  </motion.div>
                );
              })}
            </nav>
          </div>

          <div className="flex items-center space-x-4">
            <div className="hidden md:flex items-center space-x-4">
              <LanguageSwitcher />
              <ThemeToggle />
            </div>

            <button
              onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
              className="md:hidden p-2 rounded-lg card hover:bg-muted transition-all duration-300"
              aria-label="Toggle menu"
            >
              {isMobileMenuOpen ? <X className="w-5 h-5" /> : <Menu className="w-5 h-5" />}
            </button>
          </div>
        </div>

        <AnimatePresence>
          {isMobileMenuOpen && (
            <motion.nav
              initial={{ opacity: 0, height: 0 }}
              animate={{ opacity: 1, height: 'auto' }}
              exit={{ opacity: 0, height: 0 }}
              className="md:hidden mt-4 overflow-hidden"
            >
              <div className="space-y-2 py-2">
                {navItems.map((item) => {
                  const isActive = getActiveState(item.path);

                  return (
                    <Link
                      key={item.path}
                      href={item.path}
                      onClick={() => setIsMobileMenuOpen(false)}
                      className={`flex items-center gap-3 px-4 py-3 rounded-lg font-medium transition-all duration-300 w-full
                        ${
                          isActive
                            ? 'bg-accent text-accent-foreground'
                            : 'text-muted-foreground hover:text-accent hover:bg-accent/10'
                        }
                      `}
                    >
                      <item.icon className="w-5 h-5" />
                      {item.name}
                      {isActive && (
                        <div className="ml-auto w-2 h-2 rounded-full bg-accent-foreground" />
                      )}
                    </Link>
                  );
                })}

                <div className="flex items-center gap-2 pt-4 border-t border-border">
                  <LanguageSwitcher />
                  <ThemeToggle />
                </div>
              </div>
            </motion.nav>
          )}
        </AnimatePresence>
      </div>
    </motion.header>
  );
}
