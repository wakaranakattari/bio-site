'use client';

import { GitHubRepo } from '@/types';
import { Star, GitFork, Calendar, Shield, ExternalLink } from 'lucide-react';
import { formatDate } from '@/lib/utils';
import { LanguageBar } from './LanguageBar';
import { useLanguage } from '@/components/LanguageProvider';

interface ProjectCardProps {
  project: GitHubRepo;
}

export function ProjectCard({ project }: ProjectCardProps) {
  const { t } = useLanguage();

  return (
    <div className="card p-6 rounded-2xl hover:border-accent/50 transition-all duration-300 group">
      <div className="flex justify-between items-start mb-4">
        <h3 className="text-xl font-bold font-mono">
          <a
            href={project.html_url}
            target="_blank"
            rel="noopener noreferrer"
            className="hover:text-accent transition-colors flex items-center gap-2"
          >
            {project.name}
            <ExternalLink className="w-3 h-3 opacity-0 group-hover:opacity-100 transition-opacity" />
          </a>
        </h3>
        <div className="flex items-center space-x-4">
          <div className="flex items-center space-x-1">
            <Star className="w-4 h-4 text-yellow-500" />
            <span className="font-mono text-sm">{project.stargazers_count}</span>
          </div>
          <div className="flex items-center space-x-1">
            <GitFork className="w-4 h-4 text-green-500" />
            <span className="font-mono text-sm">{project.forks_count}</span>
          </div>
        </div>
      </div>

      <p className="mb-6 text-muted-foreground text-sm">
        {project.description || t('projects.noDescription')}
      </p>

      {project.languages && Object.keys(project.languages).length > 0 && (
        <div className="mb-6">
          <LanguageBar languages={project.languages} />
        </div>
      )}

      <div className="flex flex-wrap items-center justify-between pt-4 border-t border-border">
        <div className="flex items-center space-x-4">
          <div className="flex items-center space-x-1">
            <Calendar className="w-4 h-4 text-accent" />
            <span className="text-xs font-mono">{formatDate(project.updated_at)}</span>
          </div>

          {project.license && (
            <div className="flex items-center space-x-1">
              <Shield className="w-4 h-4 text-purple-500" />
              <span className="text-xs font-mono">{project.license.name}</span>
            </div>
          )}
        </div>

        <div className="flex items-center space-x-2">
          {project.topics?.slice(0, 2).map((topic) => (
            <span
              key={topic}
              className="px-2 py-1 text-xs border border-border bg-muted rounded font-mono"
            >
              {topic}
            </span>
          ))}
        </div>
      </div>
    </div>
  );
}
